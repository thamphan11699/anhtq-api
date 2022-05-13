package com.example.demo.repository;

import com.example.demo.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {



}
