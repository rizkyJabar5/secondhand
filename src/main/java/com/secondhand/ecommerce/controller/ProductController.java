package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.products.ProductDto;
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

    @Operation(summary = "List product by id")
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {

        return new ResponseEntity<>(
                productService.loadProductById(productId),
                HttpStatus.OK);
    }

    @PostMapping(value = "/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProduct(@ModelAttribute ProductDto request,
                                        @RequestParam MultipartFile[] images) {

        BaseResponse baseResponse = productService.addProduct(request, images);

        if (images.length >= 5) {
            return new ResponseEntity<>(new BaseResponse(HttpStatus.BAD_REQUEST,
                    "Maximum upload image not more than 4",
                    null,
                    OperationStatus.FAILURE), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@ModelAttribute ProductDto product,
                                           @RequestParam MultipartFile[] images) {

        BaseResponse baseResponse = productService.updateProduct(product, images);

        if (images.length >= 5) {
            return new ResponseEntity<>(new BaseResponse(HttpStatus.BAD_REQUEST,
                    "Maximum upload image not more than 4",
                    null,
                    OperationStatus.FAILURE), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
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
