package com.example.demo.dto;

import com.example.demo.model.Cart;
import com.example.demo.model.CartProduct;

import java.util.ArrayList;
import java.util.List;

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
        if (entity.getCartProducts() != null && entity.getCartProducts().size() > 0) {
            this.cartProducts = new ArrayList<>();
            this.subtotal = 0L;
            for (CartProduct cartProduct : entity.getCartProducts()) {
                if (cartProduct.getIsDelete() == null || cartProduct.getIsDelete() == 1L) {
                    cartProducts.add(new CartProductDto(cartProduct));
                    this.subtotal += cartProduct.getProduct().getPrice() * cartProduct.getAmount();
                }
            }
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
