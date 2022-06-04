package com.example.demo.repository;

import com.example.demo.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query(value = "delete from ProductCategory as enrity where enrity.product.id = ?1")
    List<Long> deleteByProductId(Long id);

}
