package com.secondhand.ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public class Datatable<T, ID> {

    protected Page<T> getSortedPaginatedProducts(JpaRepository<T, ID> repository, int page, int limit, Sort sort) {
        Pageable paging = PageRequest.of(page, limit, sort);
        return repository.findAll(paging);
    }
}
