package com.example.demo.dto;

public class DashboardDto {
    private String countOrder;
    private String countUser;
    private String revenue;
    private String saleProduct;

    public DashboardDto(String countOrder, String countUser, String revenue, String saleProduct) {
        this.countOrder = countOrder;
        this.countUser = countUser;
        this.revenue = revenue;
        this.saleProduct = saleProduct;
    }

    public DashboardDto() {
    }

    public String getCountUser() {
        return countUser;
    }

    public void setCountUser(String countUser) {
        this.countUser = countUser;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getSaleProduct() {
        return saleProduct;
    }

    public void setSaleProduct(String saleProduct) {
        this.saleProduct = saleProduct;
    }

    public String getCountOrder() {
        return countOrder;
    }

    public void setCountOrder(String countOrder) {
        this.countOrder = countOrder;
    }
}
