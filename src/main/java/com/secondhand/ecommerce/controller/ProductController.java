package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.products.ProductDto;
import com.secondhand.ecommerce.models.entity.Product;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    private final CategoriesService categoriesService;

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
    @GetMapping(
//            value = "/show/{productId}",
            value = "/show",
            produces = MediaType.IMAGE_JPEG_VALUE,
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<byte[]> getProducts(
//            @PathVariable("productId") Long productId,
            @RequestParam("productId") Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "", name = "order") String sorts
    ) {
        // Sort by comma separated values => id,desc;price,asc etc.
//
//        productService.getProducts();
//        Product product = productRepository.getById(productId);
//        InputStream in = getClass().getResourceAsStream(productRepository.toString());
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + product.getImageName() + "\"")
//                .body(product.getImageFile());

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
//
//            Map<String, Object> response = new HashMap<>();
//            Page<Product> pageProducts = productService.getSortedPaginatedProducts(page, limit, Sort.by(orders));
//
//            response.put("results", pageProducts.toList());
//            response.put("currentPage", pageProducts.getNumber());
//            response.put("totalItems", pageProducts.getTotalElements());
//            response.put("totalPages", pageProducts.getTotalPages());
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable long id) {
        Optional<Product> product = productService.deleteProductById(id);

        Map<String, Object> response = new HashMap<>();
        if (product.isPresent()) {
            response.put("success", true);
            response.put("deletedData", product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list-categories")
    public ResponseEntity<?> getAllCategories() {
        return new ResponseEntity<>(categoriesService.findAllCategories(), HttpStatus.OK);
    }
}
