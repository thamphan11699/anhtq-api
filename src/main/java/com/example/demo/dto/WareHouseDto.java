package com.example.demo.dto;

import com.example.demo.model.WareHouse;

import javax.persistence.Column;

public class WareHouseDto extends BaseDto {

    private String name;

    private String code;

    private Integer maxSize;

    private Integer currentSize;

    public WareHouseDto () {}

    public WareHouseDto (WareHouse entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.code = entity.getCode();
        this.currentSize = entity.getCurrentSize();
        this.maxSize = entity.getMaxSize();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(Integer currentSize) {
        this.currentSize = currentSize;
    }
}
