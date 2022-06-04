package com.example.demo.model;

import com.example.demo.model.audit.UserDateAudit;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "tbl_role")
public class Role extends UserDateAudit {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
