//package com.secondhand.ecommerce.controller;
//
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//import com.secondhand.ecommerce.models.dto.products.ProductRequest;
//import com.secondhand.ecommerce.models.dto.products.ProductResponse;
//import com.secondhand.ecommerce.models.entity.Product;
//import com.secondhand.ecommerce.repository.ImagesRepository;
//import com.secondhand.ecommerce.service.ProductService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/v1/products")
//public class ProductController {
//
//    private final ProductService productService;
//    private final ImagesRepository imagesRepository;
//
//    @PostMapping(value = "/add/{userId}",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map<String, Object>> addProduct(
//            @PathVariable Long userId,
//            @RequestParam ProductRequest request) {
//
//        productService.addProduct(userId, request)
//
//        return new ResponseEntity(new ProductResponse(userId, productId, name, description, price,
//                category, Arrays.asList(url)), HttpStatus.OK);
//    }
//
//
//    @PutMapping("/update")
//    public ResponseEntity<Map<String, Object>> updateProduct(@RequestBody Product product) {
//        product = productService.updateProduct(product);
//        Map<String, Object> response = new HashMap<>();
//        response.put("success", true);
//        response.put("data", product);
//        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
//    }
//
//
//    //    @Transactional
//    @GetMapping(
////            value = "/show/{productId}",
//            value = "/show",
//            produces = MediaType.IMAGE_JPEG_VALUE,
//            consumes = MediaType.ALL_VALUE
//    )
//    public ResponseEntity<byte[]> getProducts(
////            @PathVariable("productId") Long productId,
//            @RequestParam("productId") Long productId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int limit,
//            @RequestParam(defaultValue = "", name = "order") String sorts
//    ) {
//        // Sort by comma separated values => id,desc;price,asc etc.
////
////        productService.getProducts();
////        Product product = productRepository.getById(productId);
////        InputStream in = getClass().getResourceAsStream(productRepository.toString());
////        return ResponseEntity.ok()
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + product.getImageName() + "\"")
////                .body(product.getImageFile());
//
////        try {
////            List<Sort.Order> orders = new ArrayList<>();
////            if(!sorts.isEmpty()){
////                for (String sortable: sorts.split(";")) {
////                    String[] desc = sortable.split(",");
////                    String columnName = desc[0];
////                    String direction = desc.length > 1? desc[1] : "asc";
////                    orders.add(new Sort.Order(Sort.Direction.fromString(direction), columnName));
////                }
////            }
////
////            Map<String, Object> response = new HashMap<>();
////            Page<Product> pageProducts = productService.getSortedPaginatedProducts(page, limit, Sort.by(orders));
////
////            response.put("results", pageProducts.toList());
////            response.put("currentPage", pageProducts.getNumber());
////            response.put("totalItems", pageProducts.getTotalElements());
////            response.put("totalPages", pageProducts.getTotalPages());
////
////            return new ResponseEntity<>(response, HttpStatus.OK);
////        } catch (Exception e){
////            e.printStackTrace();
////            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
////        }
//        return null;
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable long id) {
//        Optional<Product> product = productService.deleteProductById(id);
//
//        Map<String, Object> response = new HashMap<>();
//        if (product.isPresent()) {
//            response.put("success", true);
//            response.put("deletedData", product);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//}
