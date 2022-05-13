package com.example.demo.service;

import com.example.demo.dto.ColorDto;
import com.example.demo.dto.SearchDto;
import org.springframework.data.domain.Page;

public interface ColorService {

    ColorDto saveOrUpdate(ColorDto dto, Long id);

    ColorDto getOne(Long id);

    Page<ColorDto> getAll(SearchDto dto);

    boolean delete(Long id);

}
