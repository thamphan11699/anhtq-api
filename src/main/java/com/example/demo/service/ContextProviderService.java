package com.example.demo.service;

import com.example.demo.dto.ContextProviderDto;
import com.example.demo.dto.SearchDto;
import org.springframework.data.domain.Page;

public interface ContextProviderService {

    ContextProviderDto saveOrUpdate(ContextProviderDto dto, Long id);
    ContextProviderDto getOne(Long id);
    Page<ContextProviderDto> getAll(SearchDto dto);
    boolean delete(Long id);

}
