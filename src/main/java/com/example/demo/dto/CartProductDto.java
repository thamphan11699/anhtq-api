package com.example.demo.dto;

import com.example.demo.model.CartProduct;

public class CartProductDto extends BaseDto {

    private ProductDto product;

    private Long amount;

    private Long isDelete;

    public CartProductDto() {
    }

    public CartProductDto(CartProduct entity) {
        this.id = entity.getId();
        this.product = new ProductDto(entity.getProduct());
        this.amount = entity.getAmount();
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Long isDelete) {
        this.isDelete = isDelete;
    }
}
