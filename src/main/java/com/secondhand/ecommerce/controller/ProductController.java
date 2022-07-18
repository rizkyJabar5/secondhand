package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.products.ProductDto;
import com.secondhand.ecommerce.models.dto.products.ProductUpdate;
import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.enums.OperationStatus;
import com.secondhand.ecommerce.service.ProductService;
import com.secondhand.ecommerce.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Product", description = "Endpoints for processing products")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Add new product")
    @PostMapping(value = "/add",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> addaddProduct(@ModelAttribute ProductDto request,
                                                      @RequestParam MultipartFile[] images) {

        BaseResponse baseResponse = productService.addProduct(request, images);

        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Update existing product with id")
    @PutMapping(value = "/update",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> updateProduct(@ModelAttribute ProductUpdate product,
                                                      @RequestParam(required = false) MultipartFile[] images) {

        BaseResponse baseResponse = productService.updateProduct(product, images);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @Operation(summary = "Update existing product with id product")
    @GetMapping("/show/{userId}")
    public ResponseEntity<BaseResponse> getProductsByUserId(@PathVariable Long userId) {

        BaseResponse response = productService.getProductsByUserId(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete existing product with id product")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CompletedResponse> deleteProduct(@PathVariable long id) {

        CompletedResponse response = productService.deleteProductById(id);

        boolean isNotFound = response
                .getStatus()
                .equals(OperationStatus.NOT_FOUND.getName());

        if (isNotFound) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Published new product with id product")
    @PutMapping("/publish")
    public ResponseEntity<BaseResponse> publishProduct(@RequestParam Long productId) {

        BaseResponse response = productService.publishedProduct(productId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "find product is sold for seller")
    @GetMapping("/sold/{userId}")
    public ResponseEntity<BaseResponse> getProductIsSoldByUser(@PathVariable Long userId) {

        BaseResponse response = productService.getProductIsSold(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
