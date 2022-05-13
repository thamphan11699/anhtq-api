package com.example.demo.dto;

import com.example.demo.model.News;

public class NewsDto extends BaseDto {

    private String title;

    private String content;

    private String thumbnail;

    public NewsDto() {
    }

    public NewsDto(News entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.thumbnail = entity.getThumbnail();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
