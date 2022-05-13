package com.example.demo.service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.SearchDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    CategoryDto saveOrUpdate(CategoryDto dto, Long id);

    Page<CategoryDto> searchByPage(SearchDto dto);

    CategoryDto getOne(Long id);

    boolean delete(Long id);

    List<CategoryDto> getByParentId(Long id);



}
