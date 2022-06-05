package com.example.demo.rest;

import com.example.demo.Constants;
import com.example.demo.dto.CartDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.SearchOrderDto;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class RestOrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("")
    @Secured({Constants.ROLE_USER})
    public ResponseEntity<OrderDto> order(@RequestBody CartDto cartDto) {
        OrderDto orderDto = orderService.addOrder(cartDto);
        return new ResponseEntity<>(orderDto, orderDto != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    @Secured({Constants.ROLE_USER, Constants.ROLE_ADMIN})
    public ResponseEntity<OrderDto> getById(@PathVariable Long id) {
        OrderDto orderDto = orderService.getById(id);
        return new ResponseEntity<>(orderDto, orderDto != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}")
    @Secured({Constants.ROLE_USER, Constants.ROLE_ADMIN})
    public ResponseEntity<OrderDto> changeStatus(@PathVariable Long id, @RequestBody OrderDto dto) {
        OrderDto orderDto = orderService.changeStatus(id, dto.getStatus(), dto.getDescription());
        return new ResponseEntity<>(orderDto, orderDto != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    @Secured({Constants.ROLE_USER, Constants.ROLE_ADMIN})
    public ResponseEntity<OrderDto> update(@PathVariable Long id, @RequestBody OrderDto dto) {
        OrderDto orderDto = orderService.updateOrder(dto, id);
        return new ResponseEntity<>(orderDto, orderDto != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/all")
    @Secured({Constants.ROLE_USER, Constants.ROLE_ADMIN})
    public ResponseEntity<Page<OrderDto>> getAll(@RequestBody SearchOrderDto searchOrderDto) {
        Page<OrderDto> orderDto = orderService.getAllOrder(searchOrderDto);
        return new ResponseEntity<>(orderDto, orderDto != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-by-user/{id}")
    @Secured({Constants.ROLE_USER})
    public ResponseEntity<List<OrderDto>> getByUserId(@PathVariable Long id) {
        List<OrderDto> orderDto = orderService.getByUserId(id);
        return new ResponseEntity<>(orderDto, orderDto != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
