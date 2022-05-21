package com.example.demo.service;

import com.example.demo.dto.ColorDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.dto.SearchProductDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductDto saveOrUpdate(ProductDto dto, Long id);
    ProductDto getOne(Long id);
    Page<ProductDto> getAll(SearchProductDto dto);
    boolean delete(Long id);

    ProductDto updateThumbnail(MultipartFile multipartFile, Long id);

    ProductDto updateImage(MultipartFile[] multipartFiles, Long id);

    List<ProductDto> getByParentId(Long o);

    List<ProductDto> getByCategory(Long cateId);

    ProductDto getOneByUser(Long id);

    ProductDto findByColorAndSize(Long parentId, ColorDto color, String size);
}
