package com.example.demo.service;

import com.example.demo.dto.CartDto;
import com.example.demo.dto.CartProductDto;
import com.example.demo.dto.ColorDto;
import com.example.demo.dto.SearchDto;
import org.springframework.data.domain.Page;

public interface CartService {

    Page<CartDto> getAllOrder(SearchDto dto);

    CartDto getById(Long id);

    CartDto getByUserId(Long id);

    CartDto addToCart(CartDto cartDto, CartProductDto cartProductDto);

    CartDto updateCart(CartDto cartDto, CartProductDto cartProductDto, Long amount);

    CartDto deleteCartItem(CartProductDto cartProductDto, Long cartId);

    CartDto updateCartAmount(CartProductDto cartProductDto, Long bonus, Long cartId);


}
