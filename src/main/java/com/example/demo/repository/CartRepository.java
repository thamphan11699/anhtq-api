package com.example.demo.repository;

import com.example.demo.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT C.* FROM tbl_cart AS C WHERE C.user_id = ?1", nativeQuery = true)
    Cart getCartByUser(Long id);

    @Query(value = "SELECT COUNT(*) FROM tbl_cart WHERE user_id = ?1", nativeQuery = true)
    Long checkCartExit(Long userId);

    @Query(value = "DELETE FROM tbl_cart_product WHERE cart_id = ?1", nativeQuery = true)
    void deleteByCart(Long cartId);

}
