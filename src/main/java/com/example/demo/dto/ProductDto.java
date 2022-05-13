package com.example.demo.dto;

import com.example.demo.model.Category;
import com.example.demo.model.Image;
import com.example.demo.model.Product;
import com.example.demo.model.ProductCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDto extends BaseDto {

    private String name;
    private String code;
    private String size;
    private String thumbnail;
    private ColorDto color;
    private List<ImageDto> images;
    private String title;
    private String description;
    private List<CategoryDto> categories = new ArrayList<>();
    private ProductDto parent;
    private Long parentId;
    private List<ProductDto> children;
    private Integer quantity;
    private Long price;
    private Long sold;

    private WareHouseDto wareHouse;
    private ContextProviderDto contextProvider;

    public ProductDto() {
    }

    public ProductDto(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.code = entity.getCode();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.description = entity.getDescription();
        this.quantity = entity.getQuantity();
        this.thumbnail = entity.getThumbnail();
        this.size = entity.getSize();
        this.price = entity.getPrice();
        if (entity.getColor() != null) {
            this.color = new ColorDto(entity.getColor());
        }
        if (entity.getParent() != null) {
            this.parent = new ProductDto(entity.getParent(), false);
            this.parentId = entity.getParent().getId();
        }
        if (entity.getCategories() != null && !entity.getCategories().isEmpty()) {
            List<CategoryDto> categoryDtos = new ArrayList<>();
            for (Category category: entity.getCategories()) {
                categoryDtos.add(new CategoryDto(category, true, ""));
            }
            this.categories = categoryDtos;
        }
        if (entity.getContextProvider() != null) {
            this.contextProvider = new ContextProviderDto(entity.getContextProvider());
        }
        if (entity.getWareHouse() != null) {
            this.wareHouse = new WareHouseDto(entity.getWareHouse());
        }
        this.sold = entity.getSold();
    }

    public ProductDto(Product entity, boolean parent) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.code = entity.getCode();
        this.size = entity.getSize();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.quantity = entity.getQuantity();
        this.thumbnail = entity.getThumbnail();
        this.price = entity.getPrice();
        if (entity.getContextProvider() != null) {
            this.contextProvider = new ContextProviderDto(entity.getContextProvider());
        }
        if (entity.getWareHouse() != null) {
            this.wareHouse = new WareHouseDto(entity.getWareHouse());
        }
        if (entity.getColor() != null) {
            this.color = new ColorDto(entity.getColor());
        }
        if (parent) {
            if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
                List<ProductDto> productDtos = new ArrayList<>();
                for (Product product: entity.getChildren()) {
                    productDtos.add(new ProductDto(product));
                }
                this.children = productDtos;
            }
            if (entity.getCategories() != null && !entity.getCategories().isEmpty()) {
                List<CategoryDto> categoryDtos = new ArrayList<>();
                for (Category category: entity.getCategories()) {
                    categoryDtos.add(new CategoryDto(category, true, ""));
                }
                this.categories = categoryDtos;
            }
        }
        if (entity.getParent() != null) {
            this.parentId = entity.getParent().getId();
        }
        this.sold = entity.getSold();
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ColorDto getColor() {
        return color;
    }

    public void setColor(ColorDto color) {
        this.color = color;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public ProductDto getParent() {
        return parent;
    }

    public void setParent(ProductDto parent) {
        this.parent = parent;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<ProductDto> getChildren() {
        return children;
    }

    public void setChildren(List<ProductDto> children) {
        this.children = children;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public WareHouseDto getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(WareHouseDto wareHouse) {
        this.wareHouse = wareHouse;
    }

    public ContextProviderDto getContextProvider() {
        return contextProvider;
    }

    public void setContextProvider(ContextProviderDto contextProvider) {
        this.contextProvider = contextProvider;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSold() {
        return sold;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }
}
