package com.secondhand.ecommerce.service.impl;

import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.repository.ProductRepository;
import com.secondhand.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    @Override
    public Product addProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public Optional<Product> deleteProductById(Long id) {
        Optional<Product> deletedProduct = productRepository.findById(id);
        productRepository.deleteById(id);
        return deletedProduct;
    }
}
