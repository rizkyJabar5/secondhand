package com.secondhand.ecommerce.service.impl;

import com.cloudinary.utils.ObjectUtils;
import com.secondhand.ecommerce.config.CloudinaryConfig;
import com.secondhand.ecommerce.exceptions.AppBaseException;
import com.secondhand.ecommerce.exceptions.IllegalException;
import com.secondhand.ecommerce.models.dto.products.ProductDto;
import com.secondhand.ecommerce.models.dto.products.ProductMapper;
import com.secondhand.ecommerce.models.dto.products.ProductUpdate;
import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.dto.response.ProductResponse;
import com.secondhand.ecommerce.models.dto.users.AppUserBuilder;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.models.enums.OperationStatus;
import com.secondhand.ecommerce.repository.ProductRepository;
import com.secondhand.ecommerce.security.SecurityUtils;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.service.CategoriesService;
import com.secondhand.ecommerce.service.Datatable;
import com.secondhand.ecommerce.service.ProductService;
import com.secondhand.ecommerce.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static com.secondhand.ecommerce.utils.SecondHandConst.EMAIL_NOT_FOUND_MSG;
import static com.secondhand.ecommerce.utils.SecondHandConst.PRODUCT_NOT_FOUND_MSG;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductServiceImpl extends Datatable<Product, Long> implements ProductService {

    private final ProductRepository productRepository;
    private final AppUserService userService;
    private final CategoriesService categoryService;
    private final CloudinaryConfig cloudinaryConfig;
    private final ProductMapper productMapper;

    @Override
    public BaseResponse getProductsByUserId(Long userId) {

        List<ProductMapper> productUser = productRepository.findProductByAppUsers(userId)
                .stream()
                .map(productMapper::productToDto)
                .collect(Collectors.toList());

        if (productUser.isEmpty()) {
            return new BaseResponse(HttpStatus.NOT_FOUND,
                    "Product not found on user: " + userId,
                    OperationStatus.NOT_FOUND);
        }

        return new BaseResponse(HttpStatus.OK,
                "Product found: " + productUser.get(0).getAddedBy(),
                productUser,
                OperationStatus.FOUND);
    }

    @Override
    public BaseResponse addProduct(ProductDto request, MultipartFile[] image) {

        AppUserBuilder builder = SecurityUtils.getAuthenticatedUserDetails();
        boolean authenticated = SecurityUtils.isAuthenticated();
        AppUsers appUsers = userService.findUserByEmail(Objects.requireNonNull(builder).getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(EMAIL_NOT_FOUND_MSG, builder.getEmail())));

        Product createNewProduct = new Product();
        Categories categories = categoryService.loadCategoryById(request.getCategoryId());

        List<String> images = new ArrayList<>();

        if (authenticated) {
            createNewProduct.setAppUsers(appUsers);
            createNewProduct.setProductName(request.getProductName());
            createNewProduct.setPrice(request.getPrice());
            createNewProduct.setDescription(request.getDescription());
            createNewProduct.setCategory(categories);
            createNewProduct.setCreatedBy(appUsers.getEmail());

            if (image.length >= 5) {
                return new BaseResponse(HttpStatus.BAD_REQUEST,
                        "Maximum upload image not more than 4",
                        null,
                        OperationStatus.FAILURE);
            }

            uploadProductImage(image, images);

            createNewProduct.setProductImages(images);
            productRepository.save(createNewProduct);
        }

        ProductResponse response = new ProductResponse(
                createNewProduct.getId(),
                createNewProduct.getProductName(),
                createNewProduct.getDescription(),
                createNewProduct.getPrice(),
                createNewProduct.getCategory().getName().name(),
                createNewProduct.getCreatedBy(),
                createNewProduct.getCreatedDate().toString(),
                images,
                createNewProduct.getIsPublished(),
                createNewProduct.getIsSold());

        return new BaseResponse(HttpStatus.CREATED,
                "Success to create new product",
                response,
                OperationStatus.SUCCESS);
    }

    @Override
    public BaseResponse updateProduct(ProductUpdate request, MultipartFile[] image) {

        boolean authenticated = SecurityUtils.isAuthenticated();

        Product updatedProduct = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppBaseException("Product not found"));

        List<String> images = new ArrayList<>();

        if (authenticated) {
            if (request.getProductName() != null) {
                updatedProduct.setProductName(request.getProductName());
            }
            if (request.getDescription() != null) {
                updatedProduct.setDescription(request.getDescription());
            }
            if (request.getPrice() != null) {
                updatedProduct.setPrice(request.getPrice());
            }
            if (request.getCategoryId() != null) {
                Categories categories = categoryService.loadCategoryById(request.getCategoryId());
                updatedProduct.setCategory(categories);
            }

            if (image != null) {
                if (image.length > 4) {
                    return new BaseResponse(HttpStatus.BAD_REQUEST,
                            "Maximum upload image not more than 4",
                            null,
                            OperationStatus.FAILURE);
                }
                uploadProductImage(image, images);
                updatedProduct.setProductImages(images);
            }

            productRepository.save(updatedProduct);
        }
        ProductResponse response = new ProductResponse(
                updatedProduct.getId(),
                updatedProduct.getProductName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getCategory().getName().name(),
                updatedProduct.getCreatedBy(),
                updatedProduct.getCreatedDate().toString(),
                images,
                updatedProduct.getIsPublished(),
                updatedProduct.getIsSold());

        return new BaseResponse(HttpStatus.OK,
                "Success to updated product",
                response,
                OperationStatus.SUCCESS);
    }

    @Override
    public CompletedResponse deleteProductById(Long id) {

        boolean present = loadProductById(id).isPresent();
        if (!present) {
            return new CompletedResponse(
                    "Product is not present",
                    OperationStatus.NOT_FOUND.getName());
        }
        productRepository.deleteById(id);

        return new CompletedResponse(
                "Product has been removed from store",
                OperationStatus.SUCCESS.getName()
        );
    }

    @Override
    public BaseResponse publishedProduct(Long productId) {
        Product publish = productRepository.findById(productId)
                .orElseThrow(() -> new AppBaseException("Product not found"));

        AppUserBuilder userDetails = SecurityUtils.getAuthenticatedUserDetails();

        Long userId = publish.getAppUsers().getUserId();
        if (!Objects.equals(userId, Objects.requireNonNull(userDetails).getUserId())) {
            return new BaseResponse(HttpStatus.BAD_REQUEST,
                    "Product it's not your own",
                    OperationStatus.FAILURE);
        }

        if (publish.getIsPublished().equals(true)) {
            return new BaseResponse(HttpStatus.NOT_ACCEPTABLE,
                    "Product is already published",
                    OperationStatus.FAILURE);
        }

        publish.setIsPublished(true);
        productRepository.save(publish);

        return new BaseResponse(HttpStatus.OK,
                "Product " + productId + " has been published.",
                OperationStatus.SUCCESS);
    }

    @Override
    public BaseResponse getProductIsSoldByUser(Long userId) {

        List<ProductMapper> productUser = productRepository.findProductisSoldByUsers(userId)
                .stream()
                .map(productMapper::productToDto)
                .collect(Collectors.toList());

        if (productUser.isEmpty()) {
            return new BaseResponse(HttpStatus.NOT_FOUND,
                    "Your product has not been sold",
                    OperationStatus.NOT_FOUND);
        }

        return new BaseResponse(HttpStatus.FOUND,
                "your sold product was found");
    }

    /**
     * UnUseless method
     */
    @Override
    public Page<Product> getSortedPaginatedProducts(int page, int limit, Sort sort) {
        return super.getSortedPaginatedProducts(productRepository, page, limit, sort);
    }

    @Override
    public Optional<ProductMapper> loadProductById(Long productId) {
        return Optional.ofNullable(productRepository.findById(productId)
                .map(productMapper::productToDto)
                .orElseThrow(() -> new IllegalException(
                        String.format(PRODUCT_NOT_FOUND_MSG, productId))));
    }

    @Override
    public Page<ProductMapper> getAllProductPageByProductNameAndCategory(String productName,
                                                                         Long categoryId,
                                                                         Pageable paging) {

        boolean productNullOrEmpty = productName == null || productName.isEmpty();
        boolean categoryNullOrEmpty = categoryId == null || org.apache.commons.lang3.ObjectUtils.isEmpty(categoryId);

        if (productNullOrEmpty && categoryNullOrEmpty) {
            Page<Product> findAllProduct = productRepository.findAll(paging);
            return findAllProduct.map(productMapper::productToDto);
        } else if (productNullOrEmpty) {
            Page<Product> byCategoryIdContaining = productRepository.findByCategoryId(categoryId, paging);
            return byCategoryIdContaining.map(productMapper::productToDto);
        } else if (categoryNullOrEmpty) {
            Page<Product> byProductNameContaining = productRepository.findByProductName(productName, paging);
            return byProductNameContaining.map(productMapper::productToDto);
        }

        Page<Product> byProductNameContainingAndCategoryIdContaining = productRepository.findByProductNameContainingIgnoreCaseAndCategoryId(productName, categoryId, paging);
        return byProductNameContainingAndCategoryIdContaining.map(productMapper::productToDto);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(PRODUCT_NOT_FOUND_MSG, productId)));
    }

    private void uploadProductImage(MultipartFile[] images, List<String> urlImages) {

        if (images != null && images.length > 0) {

            for (MultipartFile image : images) {
                if (image.isEmpty()) {
                    throw new IllegalException("The image is required to create a new a product.");
                }
                Map uploadResult = cloudinaryConfig.upload(image,
                        ObjectUtils.asMap("resourcetype", "auto"));
                urlImages.add(uploadResult.get("url").toString());
            }
        }
    }
}