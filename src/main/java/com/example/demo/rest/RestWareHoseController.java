package com.example.demo.rest;


import com.example.demo.Constants;
import com.example.demo.dto.ColorDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.dto.WareHouseDto;
import com.example.demo.service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ware-house")
public class RestWareHoseController {

    @Autowired
    WareHouseService service;

    @PostMapping()
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<WareHouseDto> save(@RequestBody WareHouseDto dto) {
        WareHouseDto result = service.saveOrUpdate(dto, null);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN, Constants.ROLE_USER})
    public ResponseEntity<WareHouseDto> update(@RequestBody WareHouseDto dto, @PathVariable Long id) {
        WareHouseDto result = service.saveOrUpdate(dto, id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<WareHouseDto> get(@PathVariable Long id) {
        WareHouseDto result = service.getOne(id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean result = service.delete(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @PostMapping("/all")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<Page<WareHouseDto>> getAll(@RequestBody SearchDto dto) {
        Page<WareHouseDto> result = service.getAll(dto);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
