package com.example.demo.dto;

import com.example.demo.model.OrderProduct;

public class OrderProductDto extends BaseDto{

    private ProductDto product;
    private Long amount;

    public OrderProductDto() {
    }

    public OrderProductDto(OrderProduct entity) {
        this.id = entity.getId();
        this.amount = entity.getAmount();
        this.product = new ProductDto(entity.getProduct());
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
}
