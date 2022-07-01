package com.secondhand.ecommerce.service.impl;

import com.cloudinary.utils.ObjectUtils;
import com.secondhand.ecommerce.config.CloudinaryConfig;
import com.secondhand.ecommerce.models.dto.products.ProductDto;
import com.secondhand.ecommerce.models.dto.response.ProductRespone;
import com.secondhand.ecommerce.models.dto.users.AppUserBuilder;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.models.entity.ProductImage;
import com.secondhand.ecommerce.models.enums.OperationStatus;
import com.secondhand.ecommerce.repository.ProductImageRepository;
import com.secondhand.ecommerce.repository.ProductRepository;
import com.secondhand.ecommerce.security.SecurityUtils;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.service.CategoriesService;
import com.secondhand.ecommerce.service.Datatable;
import com.secondhand.ecommerce.service.ProductService;
import com.secondhand.ecommerce.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.secondhand.ecommerce.utils.SecondHandConst.EMAIL_NOT_FOUND_MSG;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductServiceImpl extends Datatable<Product, Long> implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;
    private final AppUserService userService;
    private final CategoriesService categoryService;
    private final CloudinaryConfig cloudinaryConfig;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public BaseResponse addProduct(ProductDto request, MultipartFile[] image) {

        AppUserBuilder builder = SecurityUtils.getAuthenticatedUserDetails();

        boolean equalsPrincipal = Objects.requireNonNull(builder)
                .getUserId()
                .equals(request.getUserId());

        AppUsers appUsers = userService.findUserByEmail(builder.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(EMAIL_NOT_FOUND_MSG, builder.getEmail())));

        Product createNewProduct = new Product();
        Categories categories = categoryService.loadCategoryById(request.getCategoryId());
        ProductImage imageProduct = new ProductImage();
        createNewProduct.setAppUsers(appUsers);
        createNewProduct.setDescription(request.getDescription());
        createNewProduct.setCategory(categories);
        createNewProduct.setCreatedBy(appUsers.getEmail());

        List<MultipartFile> imageFiles = new ArrayList<>();
        List<String> images = new ArrayList<>();
        if (equalsPrincipal) {
            if (image != null) {
                imageFiles
                        .forEach(file -> {
                            try {
                                Map uploadResult = cloudinaryConfig.upload(
                                        file.getBytes(),
                                        ObjectUtils.asMap("resourcetype", "filename"));
                                images.add(uploadResult.get("url").toString());
                                imageProduct.setImageName(uploadResult.get("filename").toString());
                                imageProduct.setUrlFile(images.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            imageRepository.save(imageProduct);
                        });

//                for (MultipartFile file : image) {
//                    try {
//                        Map uploadResult = cloudinaryConfig.upload(
//                                file.getBytes(),
//                                ObjectUtils.asMap("resourcetype", "filename"));
//                        images.add(uploadResult.get("url").toString());
//                        imageProduct.setUrlFile(String.valueOf(images));
//                        imageProduct.setCreatedBy(appUsers.getEmail());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                imageRepository.save(imageProduct);
            }
            createNewProduct.setProductImage(imageProduct);
            productRepository.save(createNewProduct);
        }

        ProductRespone response = new ProductRespone(
                createNewProduct.getAppUsers().getEmail(),
                createNewProduct.getProductName(),
                createNewProduct.getDescription(),
                createNewProduct.getPrice(),
                createNewProduct.getCategory().getName(),
                createNewProduct.getCreatedBy(),
                createNewProduct.getCreatedDate().toString(),
                Arrays.toString(new List[]{imageFiles})
        );

        return new BaseResponse(HttpStatus.OK,
                "Success to create new product",
                response,
                OperationStatus.SUCCESS);
    }

    @Override
    public Product updateProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public Optional<Product> deleteProductById(Long id) {
        Optional<Product> deletedProduct = productRepository.findById(id);
        productRepository.deleteById(id);
        return deletedProduct;
    }

    @Override
    public ProductImage saveProductImage(ProductImage productImage) {
        imageRepository.save(productImage);
        return productImage;
    }

    public Page<Product> getSortedPaginatedProducts(int page, int limit, Sort sort) {
        return super.getSortedPaginatedProducts(productRepository, page, limit, sort);
    }
}
