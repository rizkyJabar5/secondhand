package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.products.ProductDto;
import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.enums.OperationStatus;
import com.secondhand.ecommerce.service.CategoriesService;
import com.secondhand.ecommerce.service.ProductService;
import com.secondhand.ecommerce.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    private final CategoriesService categoriesService;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {

        return new ResponseEntity<>(
                productService.loadProductById(productId),
                HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {

        return new ResponseEntity<>(
                productService.getAllProducts(),
                HttpStatus.OK);
    }

    @PostMapping(value = "/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> addProduct(@ModelAttribute ProductDto request,
                                                   @RequestParam MultipartFile[] images) {

        if (images.length >= 5) {
            return new ResponseEntity<>(new BaseResponse(HttpStatus.BAD_REQUEST,
                    "Maximum upload image not more than 4",
                    null,
                    OperationStatus.FAILURE), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(productService.addProduct(request, images), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> updateProduct(@ModelAttribute ProductDto product,
                                                      @RequestParam MultipartFile[] images) {

        if (images.length >= 5) {
            return new ResponseEntity<>(new BaseResponse(HttpStatus.BAD_REQUEST,
                    "Maximum upload image not more than 4",
                    null,
                    OperationStatus.FAILURE), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(productService.updateProduct(product, images), HttpStatus.OK);
    }

    @GetMapping("/show/{userId}")
    public ResponseEntity<?> getProductsByUserId(@PathVariable Long userId) {

        BaseResponse response = productService.getProductsByUserId(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {

        CompletedResponse response = productService.deleteProductById(id);

        boolean isNotFound = response
                .getStatus()
                .equals(OperationStatus.NOT_FOUND.getName());
        if (isNotFound) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
