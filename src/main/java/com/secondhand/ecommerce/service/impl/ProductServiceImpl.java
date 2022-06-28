package com.secondhand.ecommerce.service.impl;

import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.models.entity.ProductImage;
import com.secondhand.ecommerce.repository.ProductImageRepository;
import com.secondhand.ecommerce.repository.ProductRepository;
import com.secondhand.ecommerce.service.ProductService;
import com.secondhand.ecommerce.utils.Datatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl extends Datatable<Product, Long> implements ProductService {

    @Autowired
    ProductRepository repository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public List<Product> getProducts() {
        return repository.findAll();
    }


    public Optional<Product> getProductById(Long id){
        return repository.findById(id);
    }

    @Override
    public Product addProduct(Product product) {
        repository.save(product);
        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        repository.save(product);
        return product;
    }

    @Override
    public Optional<Product> deleteProductById(Long id) {
        Optional<Product> deletedProduct = repository.findById(id);
        repository.deleteById(id);
        return deletedProduct;
    }

    @Override
    public ProductImage saveProductImage(ProductImage productImage) {
        productImageRepository.save(productImage);
        return productImage;
    }

    public Page<Product> getSortedPaginatedProducts(int page, int limit, Sort sort) {
        return super.getSortedPaginatedProducts(repository, page, limit, sort);
    }
}
