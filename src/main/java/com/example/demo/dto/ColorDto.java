package com.example.demo.dto;

import com.example.demo.model.Color;

public class ColorDto extends BaseDto{

    private String name;
    private String code;


    public ColorDto() {

    }

    public ColorDto(Color entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
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
}
