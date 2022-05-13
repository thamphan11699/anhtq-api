package com.example.demo.service;

import com.example.demo.dto.SearchDto;
import com.example.demo.dto.UserDto;
import org.springframework.data.domain.Page;

public interface UserService {

    UserDto getOne(Long id);

    UserDto saveOrUpdate(UserDto userDto, Long id);

    boolean delete(Long id);

    Page<UserDto> getAll(SearchDto dto);

}
