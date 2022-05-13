package com.example.demo.service;

import com.example.demo.dto.NewsDto;
import com.example.demo.dto.SearchDto;
import org.springframework.data.domain.Page;

public interface NewsService {

    NewsDto saveOrUpdate(NewsDto dto, Long id);

    NewsDto getOne(Long id);

    Page<NewsDto> getAll(SearchDto dto);

    boolean delete(Long id);
}
