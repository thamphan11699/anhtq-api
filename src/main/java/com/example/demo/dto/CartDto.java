package com.example.demo.dto;

import com.example.demo.model.Cart;
import com.example.demo.model.CartProduct;

import java.util.*;

public class CartDto extends BaseDto {

    private UserDto user;
    private List<CartProductDto> cartProducts;
    private CartProductDto cartProduct;
    private Long subtotal;

    public CartDto() {
    }

    public CartDto(Cart entity) {
        this.id = entity.getId();
        this.user = new UserDto(entity.getUser());
        if (entity.getCartProducts() != null && !entity.getCartProducts().isEmpty()) {
            this.cartProducts = new ArrayList<>();
            this.subtotal = 0L;
            ArrayList<CartProductDto> cartProductDtos = new ArrayList<>();
            for (CartProduct cartProduct : entity.getCartProducts()) {
                if (cartProduct.getIsDelete() == null || cartProduct.getIsDelete() == 1L) {
                    cartProductDtos.add(new CartProductDto(cartProduct));
                    this.subtotal += cartProduct.getProduct().getPrice() * cartProduct.getAmount();
                }
            }
            Collections.sort(cartProductDtos, new Comparator<CartProductDto>() {
                @Override
                public int compare(CartProductDto o1, CartProductDto o2) {
                    return o2.id.compareTo(o1.id);
                }
            });
            this.cartProducts = cartProductDtos;
        }
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<CartProductDto> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProductDto> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public CartProductDto getCartProduct() {
        return cartProduct;
    }

    public void setCartProduct(CartProductDto cartProduct) {
        this.cartProduct = cartProduct;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }

}
