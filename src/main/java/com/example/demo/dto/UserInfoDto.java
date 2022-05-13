package com.example.demo.dto;

import com.example.demo.model.UserInfo;

import java.util.Date;

public class UserInfoDto extends BaseDto{
    private String fullName;
    private Date birthOfDate;
    private String gender;
    private String address;
    private String phoneNumber;

    public UserInfoDto() {
    }

    public UserInfoDto(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.fullName = userInfo.getFullName();
        this.birthOfDate = userInfo.getBirthOfDate();
        this.gender = userInfo.getGender();
        this.address = userInfo.getAddress();
        this.phoneNumber = userInfo.getPhoneNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(Date birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
