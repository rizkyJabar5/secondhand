package com.secondhand.ecommerce.service.impl;

import com.cloudinary.utils.ObjectUtils;
import com.secondhand.ecommerce.config.CloudinaryConfig;
import com.secondhand.ecommerce.exceptions.AppBaseException;
import com.secondhand.ecommerce.exceptions.IllegalException;
import com.secondhand.ecommerce.models.dto.products.ProductDto;
import com.secondhand.ecommerce.models.dto.products.ProductMapper;
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
    public List<ProductMapper> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToDto)
                .collect(Collectors.toList());
    }

    public BaseResponse getProductById(Long productId) {
        return null;
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

            uploadProductImage(image, images);

            createNewProduct.setProductImages(images);
            productRepository.save(createNewProduct);
        }

        ProductResponse response = new ProductResponse(
                createNewProduct.getAppUsers().getEmail(),
                createNewProduct.getProductName(),
                createNewProduct.getDescription(),
                createNewProduct.getPrice(),
                createNewProduct.getCategory().getName().name(),
                createNewProduct.getCreatedBy(),
                createNewProduct.getCreatedDate().toString(),
                images);

        return new BaseResponse(HttpStatus.CREATED,
                "Success to create new product",
                response,
                OperationStatus.SUCCESS);
    }

    @Override
    public BaseResponse updateProduct(ProductDto request, MultipartFile[] image) {

        boolean authenticated = SecurityUtils.isAuthenticated();

        Product updatedProduct = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppBaseException("Product not found"));

        Categories categories = categoryService.loadCategoryById(request.getCategoryId());

        List<String> images = new ArrayList<>();

        if (authenticated) {
            updatedProduct.setProductName(request.getProductName());
            updatedProduct.setDescription(request.getDescription());
            updatedProduct.setPrice(request.getPrice());
            updatedProduct.setCategory(categories);

            uploadProductImage(image, images);
            updatedProduct.setProductImages(images);

            productRepository.save(updatedProduct);
        }
        ProductResponse response = new ProductResponse(
                updatedProduct.getProductName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getCategory().getName().name(),
                updatedProduct.getCreatedBy(),
                updatedProduct.getCreatedDate().toString(),
                images);

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
    public Page<Product> getProductsPage(String productName, Categories category, Pageable pageable) {
        return null;
    }

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

    private void uploadProductImage(MultipartFile[] image, List<String> images) {
        Arrays.stream(image)
                .limit(4)
                .filter(file -> {
                    if (file.isEmpty()) {
                        throw new IllegalException("The file is required to create a new");
                    }
                    return true;
                })
                .forEach(file -> {
                    Map uploadResult = cloudinaryConfig.upload(file,
                            ObjectUtils.asMap("resourcetype", "auto"));
                    images.add(uploadResult.get("url").toString());
                });
    }
}