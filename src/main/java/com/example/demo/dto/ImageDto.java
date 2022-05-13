package com.example.demo.dto;

import com.example.demo.model.Image;

public class ImageDto extends BaseDto{

    private String name;
    private String url;
    private String title;

    public ImageDto() {}

    public ImageDto(Image entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.url = entity.getUrl();
        this.title = entity.getTitle();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
