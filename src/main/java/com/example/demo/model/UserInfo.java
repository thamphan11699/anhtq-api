package com.example.demo.model;

import com.example.demo.model.audit.UserDateAudit;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_user_info")
public class UserInfo extends UserDateAudit {


    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birth")
    private Date birthOfDate;

    @Column(name = "gender")
    private String gender; // M F U

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
