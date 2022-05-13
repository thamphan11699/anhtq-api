package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM tbl_order WHERE user_id = ?1", nativeQuery = true)
    List<Order> getOrderByUser(Long userId);

    @Query(value = "SELECT COUNT(DISTINCT o.id) AS countOrder, COUNT(DISTINCT u.id) AS countUser, SUM(DISTINCT p.sold) AS saleProduct, SUM(DISTINCT o.income) AS revenue FROM tbl_order AS o, tbl_user AS u, tbl_product AS p", nativeQuery = true)
    Object getDashboard();

}
