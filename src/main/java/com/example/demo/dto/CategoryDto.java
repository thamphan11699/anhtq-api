package com.example.demo.dto;

import com.example.demo.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDto extends BaseDto {

    private String name;
    private String code;
    private String description;
    private CategoryDto parent;
    private List<CategoryDto> children;
    private Long parentId;
    private String thumbnail;

    public CategoryDto() {

    }

    public CategoryDto(Category entity, boolean isParent) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.updateBy = entity.getUpdatedBy();
        this.createBy = entity.getCreatedBy();
        this.createDate = entity.getCreatedAt();
        this.updateDate = entity.getUpdatedAt();
        this.thumbnail = entity.getThumbnail();
        if (isParent) {
            if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
                List<CategoryDto> list = new ArrayList<>();
                for (Category category : entity.getChildren()) {
                    list.add(new CategoryDto(category, true));
                }
                this.children = list;
            }
        } else {
            if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
                List<CategoryDto> list = new ArrayList<>();
                for (Category category : entity.getChildren()) {
                    list.add(new CategoryDto(category));
                }
                this.children = list;
            }
        }
    }
    public CategoryDto(Category entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.thumbnail = entity.getThumbnail();
        if (entity.getParent() != null) {
            this.parentId = entity.getParent().getId();
            this.parent = new CategoryDto(entity.getParent());
        }
        this.updateBy = entity.getUpdatedBy();
        this.createBy = entity.getCreatedBy();
        this.createDate = entity.getCreatedAt();
        this.updateDate = entity.getUpdatedAt();
    }

    public CategoryDto(Category entity, boolean test, String pr) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.thumbnail = entity.getThumbnail();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CategoryDto> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryDto> children) {
        this.children = children;
    }

    public CategoryDto getParent() {
        return parent;
    }

    public void setParent(CategoryDto parent) {
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
