package com.example.demo.repository;

import com.example.demo.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "DELETE from Image as entity WHERE entity.product.id = ?1")
    Integer deleteByProductId(Long productId);
}
