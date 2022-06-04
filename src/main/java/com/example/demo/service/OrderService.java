package com.example.demo.service;

import com.example.demo.dto.CartDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.dto.SearchOrderDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    Page<OrderDto> getAllOrder(SearchOrderDto dto);

    OrderDto getById(Long id);

    List<OrderDto> getByUserId(Long id);

    OrderDto addOrder(CartDto cartDto);

    OrderDto changeStatus(Long id, Long status, String desc);

    OrderDto updateOrder(OrderDto orderDto, Long id);

}
