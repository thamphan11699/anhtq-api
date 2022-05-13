package com.example.demo.service;

import com.example.demo.dto.RoleDto;
import com.example.demo.dto.SearchDto;
import org.springframework.data.domain.Page;

public interface RoleService {

    RoleDto saveOrUpdate(RoleDto roleDto, Long id);
    Page<RoleDto> getRoles(SearchDto dto);
    boolean delete(Long id);
    RoleDto getOne(Long id);
}
