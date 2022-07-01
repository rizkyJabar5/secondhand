package com.secondhand.ecommerce.controller;


import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.repository.ProductRepository;
import com.secondhand.ecommerce.service.CategoriesService;
import com.secondhand.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    @GetMapping
    public Page<Product> home(
            @RequestParam(defaultValue = "", required = false) String productName,
            @RequestParam(defaultValue = "", required = false) String category,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "name, asc", required = false) String[] sort
    ) {
        List<Order> orders = new ArrayList<>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Order(Sort.Direction.fromString(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Order(Sort.Direction.fromString(sort[1]), sort[0]));
        }
//        List<Categories> allCategories = categoriesService.findAllCategories();
        return productRepository.findByNameIgnoreCaseAndCategoryIgnoreCase(productName, category, PageRequest.of(page, size, Sort.by(orders)));
    }

}