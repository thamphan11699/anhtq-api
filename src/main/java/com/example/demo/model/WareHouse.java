package com.example.demo.model;

import com.example.demo.model.audit.UserDateAudit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tbl_ware_house")
@XmlRootElement
public class WareHouse extends UserDateAudit {

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "max_size")
    private Integer maxSize;

    @Column(name = "current_size")
    private Integer currentSize;

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
