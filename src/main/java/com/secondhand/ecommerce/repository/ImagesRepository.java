package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Images,Long> {
}
