package com.secondhand.ecommerce.controller;


import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.repository.ProductRepository;
import com.secondhand.ecommerce.service.CategoriesService;
import com.secondhand.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.secondhand.ecommerce.utils.SecondHandConst.HOME_PAGE;

@RestController
@RequiredArgsConstructor
@RequestMapping(HOME_PAGE)
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private final CategoriesService categoriesService;

    //Sort, Seacrhing, Filter with Name
//    @GetMapping
//    public Page<Product> home(
//            @RequestParam(required = false) String productName,
//            @RequestParam(required = false) Categories categoryId,
//            @RequestParam int page,
//            @RequestParam int size,
//            @RequestParam(defaultValue = "name, asc", required = false) String[] sort
//    ) {
//        List<Order> orders = new ArrayList<>();
//        if (sort[0].contains(",")) {
//            for (String sortOrder : sort) {
//                String[] _sort = sortOrder.split(",");
//                orders.add(new Order(Sort.Direction.fromString(_sort[1]), _sort[0]));
//            }
//        } else {
//            orders.add(new Order(Sort.Direction.fromString(sort[1]), sort[0]));
//        }
//        List<Categories> allCategories = categoriesService.findAllCategories();
//        return productRepository.findByProductNameIgnoreCaseAndCategoryIgnoreCase(productName, categoryId, PageRequest.of(page, size, Sort.by(orders)));
//    }

//    @GetMapping
//    public ResponseEntity<Map<String, Object>> home(
//            @RequestParam(required = false) String productName,
//            @RequestParam(required = false) Categories category,
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "5")int size
//    ) {
//        try {
//            Pageable paging = PageRequest.of(page - 1, size, Sort.by("price"));
//
//            Page<Product> productPage = productService.getAllProductPageByProductNameAndProductCategory(productName, category, paging);
//            List<Product> products = productPage.getContent();
//            Map<String, Object> response = new HashMap<>();
//            response.put("products", products);
//            response.put("currentPage", productPage.getNumber() + 1);
//            response.put("totalProducts", productPage.getTotalElements());
//            response.put("totalPages", productPage.getTotalPages());
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @GetMapping
//    public ResponseEntity<Product> home(){
//        Map<String, Object> response = new HashMap<>();
//            Page<Product> pageProducts = (Page<Product>) productService.getProducts();
//
//            response.put("results", pageProducts.toList());
//            response.put("currentPage", pageProducts.getNumber());
//            response.put("totalItems", pageProducts.getTotalElements());
//            response.put("totalPages", pageProducts.getTotalPages());
//        return  new ResponseEntity(productService.getProducts(), HttpStatus.OK);
//        return null;
//    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> home(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Categories categoryId,
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
}
