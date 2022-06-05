package com.example.demo.dto;

import com.example.demo.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDto extends BaseDto {

    private UserDto user;
    private List<OrderProductDto> orderProducts;
    private Long status;
    private String code;
    private String description;
    private Long income;

    private String statusText;

    public OrderDto() {
    }

    public OrderDto(Order entity) {
        this.income = entity.getIncome();
        this.code = entity.getCode();
        this.id = entity.getId();
        this.user = entity.getUser() != null ? new UserDto(entity.getUser()) : null;
        if (entity.getOrderProducts() != null && entity.getOrderProducts().size() > 0) {
            orderProducts = new ArrayList<>();
            entity.getOrderProducts().forEach((item) -> orderProducts.add(new OrderProductDto(item)));
        }
        this.status = entity.getStatus();
        this.description = entity.getDescription();
        this.statusText = entity.getStatus() == 1 ? "CREATED" : entity.getStatus() == 2 ? "ACCEPTED" : entity.getStatus() == 3 ? "PENDING" : entity.getStatus() == 4 ? "SHIPPING" : entity.getStatus() == 5 ? "RECEIVED" : entity.getStatus() == 6 ? "CANCLE" : "";
    }

    public List<OrderProductDto> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductDto> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
