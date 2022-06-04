package com.example.demo.rest;


import com.example.demo.Constants;
import com.example.demo.dto.ColorDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.RoleDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/color")
public class RestColorController {

    @Autowired
    ColorService service;

    @PostMapping()
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<ColorDto> save(@RequestBody ColorDto dto) {
        ColorDto result = service.saveOrUpdate(dto, null);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN, Constants.ROLE_USER})
    public ResponseEntity<ColorDto> update(@RequestBody ColorDto dto, @PathVariable Long id) {
        ColorDto result = service.saveOrUpdate(dto, id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<ColorDto> get(@PathVariable Long id) {
        ColorDto result = service.getOne(id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean result = service.delete(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @PostMapping("/all")
    @Secured({Constants.ROLE_ADMIN, Constants.ROLE_USER})
    public ResponseEntity<Page<ColorDto>> getAll(@RequestBody SearchDto dto) {
        Page<ColorDto> result = service.getAll(dto);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
