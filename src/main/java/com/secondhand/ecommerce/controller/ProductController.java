package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    final ProductServiceImpl productService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> addProduct(@RequestBody Product product){
        product = productService.addProduct(product);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", product);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "", name = "order") String sorts
    ){

        // Sort by comma separated values => id,desc;price,asc etc.

        try {
            List<Sort.Order> orders = new ArrayList<>();
            if(!sorts.isEmpty()){
                for (String sortable: sorts.split(";")) {
                    String[] desc = sortable.split(",");
                    String columnName = desc[0];
                    String direction = desc.length > 1? desc[1] : "asc";
                    orders.add(new Sort.Order(Sort.Direction.fromString(direction), columnName));
                }
            }

            Map<String, Object> response = new HashMap<>();
            Page<Product> pageProducts = productService.getSortedPaginatedProducts(page, limit, Sort.by(orders));

            response.put("results", pageProducts.toList());
            response.put("currentPage", pageProducts.getNumber());
            response.put("totalItems", pageProducts.getTotalElements());
            response.put("totalPages", pageProducts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
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
