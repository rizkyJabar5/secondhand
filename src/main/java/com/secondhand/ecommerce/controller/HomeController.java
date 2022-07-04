package com.secondhand.ecommerce.controller;


import com.secondhand.ecommerce.models.dto.response.ProductResponse;
import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.repository.ProductRepository;
import com.secondhand.ecommerce.service.CategoriesService;
import com.secondhand.ecommerce.service.ProductService;
import com.secondhand.ecommerce.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.secondhand.ecommerce.utils.SecondHandConst.HOME_PAGE;

@RestController
@RequiredArgsConstructor
@RequestMapping(HOME_PAGE)
public class HomeController {

    private final CategoriesService categoriesService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @ModelAttribute
    @GetMapping
    public ResponseEntity<Map<String, Object>> home(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        try {
            Pageable paging = PageRequest.of(page - 1, size, Sort.by("price"));

            Page<Product> productPage = productService.getAllProductPageByProductNameAndProductCategory(productName, categoryId, paging);
            List<Product> products = productPage.getContent();
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

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {

        return new ResponseEntity<>(
                categoriesService.findAllCategories(),
                HttpStatus.OK);
    }
}
