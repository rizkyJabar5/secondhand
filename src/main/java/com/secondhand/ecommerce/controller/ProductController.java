package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    @GetMapping("/")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

}
