package com.example.demo.rest;


import com.example.demo.Constants;
import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class RestCategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping()
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto dto) {
        CategoryDto result = categoryService.saveOrUpdate(dto, null);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN, Constants.ROLE_USER})
    public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto dto, @PathVariable Long id) {
        CategoryDto result = categoryService.saveOrUpdate(dto, id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
//    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<CategoryDto> get(@PathVariable Long id) {
        CategoryDto result = categoryService.getOne(id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean result = categoryService.delete(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @PostMapping("/all")
//    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<Page<CategoryDto>> getAll(@RequestBody SearchDto dto) {
        Page<CategoryDto> result = categoryService.searchByPage(dto);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/parent/{id}")
//    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<List<CategoryDto>> getByParent(@PathVariable Long id) {
        List<CategoryDto> result = categoryService.getByParentId(id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/get-all")
//    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<List<CategoryDto>> getAllParent(@RequestBody SearchDto dto) {
        List<CategoryDto> result = null;
        if (dto != null) {
             result = categoryService.getByParentId(null);
        }
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
