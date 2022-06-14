package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT P.* FROM tbl_product AS P JOIN tbl_product_category AS PC ON PC.product_id = P.id JOIN tbl_category AS C ON C.id = PC.category_id \n" +
            "WHERE (P.deleted = false OR p.deleted is null) AND P.parent_id is NULL AND PC.category_id IN (?1)", nativeQuery = true)
    List<Product> getProductsByCategory(Long categoryId);

    @Query(value = "SELECT P.* FROM tbl_product AS P  \n" +
            "WHERE (P.deleted = false OR p.deleted is null) AND P.parent_id is NULL", nativeQuery = true)
    List<Product> getProductsByAllCategory();

    @Query(value = "SELECT P.* FROM tbl_product AS P  \n" +
            "WHERE (P.deleted = false OR p.deleted is null) AND P.parent_id is NULL AND P.name LIKE %?1%", nativeQuery = true)
    List<Product> getProductsByAllCategory(String text);

    @Query(value = "select P.* from tbl_product AS P where P.sold is not null", nativeQuery = true)
    List<Product> getProductsBySold();
}
