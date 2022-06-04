package com.example.demo.rest;


import com.example.demo.Constants;
import com.example.demo.dto.*;
import com.example.demo.service.ColorService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class RestProductController {
    @Autowired
    ProductService service;

    @PostMapping()
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<ProductDto> save(@RequestBody ProductDto dto) {
        ProductDto result = service.saveOrUpdate(dto, null);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN, Constants.ROLE_USER})
    public ResponseEntity<ProductDto> update(@RequestBody ProductDto dto, @PathVariable Long id) {
        ProductDto result = service.saveOrUpdate(dto, id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/product-thumbnail/{id}")
    @Secured({Constants.ROLE_ADMIN, Constants.ROLE_USER})
    public ResponseEntity<ProductDto> updateThumbnail(@RequestParam("file")MultipartFile file, @PathVariable Long id) {
        ProductDto result = service.updateThumbnail(file, id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/product-image/{id}")
    @Secured({Constants.ROLE_ADMIN, Constants.ROLE_USER})
    public ResponseEntity<ProductDto> updateImage(@RequestParam("files")MultipartFile[] files, @PathVariable Long id) {
        ProductDto result = service.updateImage(files, id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<ProductDto> get(@PathVariable Long id) {
        ProductDto result = service.getOne(id);
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
    public ResponseEntity<Page<ProductDto>> getAll(@RequestBody SearchProductDto dto) {
        Page<ProductDto> result = service.getAll(dto);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/get-all")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<List<ProductDto>> getAllParent(@RequestBody SearchDto dto) {
        List<ProductDto> result = null;
        if (dto != null) {
            result = service.getByParentId(null);
        }
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
