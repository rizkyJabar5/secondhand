package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.products.ProductDto;
import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.enums.OperationStatus;
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

    //    @Transactional
    @GetMapping("/show/{userId}")
    public ResponseEntity<?> getProductsByUserId(@PathVariable Long userId,
                                                 @RequestParam(defaultValue = "0", required = false) int page,
                                                 @RequestParam(defaultValue = "10", required = false) int limit,
                                                 @RequestParam(defaultValue = "productName, asc") String sorts) {

        // Sort by comma separated values => id,desc;price,asc etc.
        BaseResponse response = productService.getProductsByUserId(userId);

//        try {
//            List<Sort.Order> orders = new ArrayList<>();
//            if(!sorts.isEmpty()){
//                for (String sortable: sorts.split(";")) {
//                    String[] desc = sortable.split(",");
//                    String columnName = desc[0];
//                    String direction = desc.length > 1? desc[1] : "asc";
//                    orders.add(new Sort.Order(Sort.Direction.fromString(direction), columnName));
//                }
//            }

        return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e){
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
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
