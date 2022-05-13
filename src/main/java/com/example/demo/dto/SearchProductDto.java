package com.example.demo.dto;

public class SearchProductDto extends SearchDto{

    private CategoryDto category;

    private String useParent;

    private ColorDto color;

    private String size;

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public String getUseParent() {
        return useParent;
    }

    public void setUseParent(String useParent) {
        this.useParent = useParent;
    }

    public ColorDto getColor() {
        return color;
    }

    public void setColor(ColorDto color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
