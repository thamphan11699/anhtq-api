package com.example.demo.rest;

import com.example.demo.Constants;
import com.example.demo.dto.RoleDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping()
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<RoleDto> save(@RequestBody RoleDto dto) {
        RoleDto result = roleService.saveOrUpdate(dto, null);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<RoleDto> update(@RequestBody RoleDto dto, @PathVariable Long id) {
        RoleDto result = roleService.saveOrUpdate(dto, id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<RoleDto> get(@PathVariable Long id) {
        RoleDto result = roleService.getOne(id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean result = roleService.delete(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @PostMapping("/all")
//    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<Page<RoleDto>> getAll(@RequestBody SearchDto dto) {
        Page<RoleDto> result = roleService.getRoles(dto);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
