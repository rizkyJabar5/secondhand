package com.secondhand.ecommerce.controller;


import com.secondhand.ecommerce.models.dto.products.ProductMapper;
import com.secondhand.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.secondhand.ecommerce.utils.SecondHandConst.HOME_PAGE;

@RestController
@RequiredArgsConstructor
@RequestMapping(HOME_PAGE)
public class HomeController {
    private final ProductService productService;

    @Operation(summary = "Sorting product by price, with pagination, and filter by name and category id")
    @ModelAttribute
    @GetMapping
    public ResponseEntity<Map<String, Object>> home(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size) {

        try {
            Pageable paging = PageRequest.of(page - 1, size, Sort.by("price"));

            Page<ProductMapper> productPage = productService.getAllProductPageByProductNameAndCategory(productName, categoryId, paging);
            List<ProductMapper> products = productPage.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("products", products);
            response.put("currentPage", productPage.getNumber() + 1);
            response.put("totalProducts", productPage.getTotalElements());
            response.put("totalPages", productPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Detail product by id")
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {

        return new ResponseEntity<>(
                productService.loadProductById(productId),
                HttpStatus.OK);
    }

}
