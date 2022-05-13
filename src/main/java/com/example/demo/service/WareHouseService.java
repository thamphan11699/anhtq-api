package com.example.demo.service;

import com.example.demo.dto.SearchDto;
import com.example.demo.dto.WareHouseDto;
import com.example.demo.model.WareHouse;
import org.springframework.data.domain.Page;

public interface WareHouseService {

    WareHouseDto saveOrUpdate(WareHouseDto dto, Long id);

    WareHouseDto getOne(Long id);

    Page<WareHouseDto> getAll(SearchDto dto);

    boolean delete(Long id);

}
