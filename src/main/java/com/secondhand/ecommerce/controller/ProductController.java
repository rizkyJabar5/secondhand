package com.secondhand.ecommerce.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.models.entity.UploadResponse;
import com.secondhand.ecommerce.repository.ImagesRepository;
import com.secondhand.ecommerce.repository.ProductRepository;
import com.secondhand.ecommerce.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    final ProductServiceImpl productService;
    final ImagesRepository imagesRepository;
    final ProductRepository productRepository;

    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "secondhandk2",
            "api_key", "612217844351516",
            "api_secret", "Efcd1QUtlgJO7FAdO4M5Vw7hag8"));

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("image") MultipartFile image, @RequestParam String name,
                                                           @RequestParam Long price, @RequestParam String description) throws IOException {
        Map<String, Object> response = new HashMap<>();
        UploadResponse responses = new UploadResponse();
        File file = new File(image.getOriginalFilename());
        FileOutputStream os = new FileOutputStream(file);
        os.write(image.getBytes());
        os.close();
        Map result = cloudinary.uploader().upload(file,
                ObjectUtils.asMap("image_id", "product_image"));
        responses.setMessage("Upload successful");
        String[] url = new String[1];
        url[0] = result.get("url").toString();
        responses.setUrl(url);

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.product(image.getOriginalFilename(),image.getBytes());
        product.setUrl(url[0]);
        productService.addProduct(product);

        response.put("success", true);
        response.put("responses", responses);
        response.put("data",product);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>>updateProduct(@RequestBody Product product){
        product = productService.updateProduct(product);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", product);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
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
    ){
        // Sort by comma separated values => id,desc;price,asc etc.

        productService.getProducts();
        Product product = productRepository.getById(productId);
        InputStream in = getClass().getResourceAsStream(productRepository.toString());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + product.getImageName() + "\"")
                .body(product.getImageFile());

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
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable long id){
        Optional<Product> product = productService.deleteProductById(id);

        Map<String, Object> response = new HashMap<>();
        if(product.isPresent()){
            response.put("success", true);
            response.put("deletedData", product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
