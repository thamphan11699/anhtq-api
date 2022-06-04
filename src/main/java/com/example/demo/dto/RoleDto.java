package com.example.demo.dto;

import com.example.demo.model.Role;

public class RoleDto extends BaseDto{

    private String name;
    private String description;

    public RoleDto() {
    }

    public RoleDto (Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.description = role.getDescription();
        this.createBy = role.getCreatedBy();
        this.updateBy = role.getUpdatedBy();
        this.createDate = role.getCreatedAt();
        this.updateDate = role.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
