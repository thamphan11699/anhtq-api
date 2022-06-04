package com.example.demo.dto;


import com.example.demo.model.ContextProvider;

public class ContextProviderDto extends BaseDto{

    private String name;
    private String code;
    private String address;
    private String description;
    private String phoneNumber;
    private String representative;

    public  ContextProviderDto() {

    }

    public ContextProviderDto(ContextProvider entity) {
        this.name = entity.getName();
        this.code = entity.getCode();
        this.address = entity.getCode();
        this.description = entity.getDescription();
        this.id  = entity.getId();
        this.phoneNumber = entity.getPhoneNumber();
        this.representative = entity.getRepresentative();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }
}
