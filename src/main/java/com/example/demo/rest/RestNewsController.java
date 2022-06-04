package com.example.demo.rest;

import com.example.demo.Constants;
import com.example.demo.dto.NewsDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/news")
public class RestNewsController {
    @Autowired
    NewsService service;

    @PostMapping()
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<NewsDto> save(@RequestBody NewsDto dto) {
        NewsDto result = service.saveOrUpdate(dto, null);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN, Constants.ROLE_USER})
    public ResponseEntity<NewsDto> update(@RequestBody NewsDto dto, @PathVariable Long id) {
        NewsDto result = service.saveOrUpdate(dto, id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<NewsDto> get(@PathVariable Long id) {
        NewsDto result = service.getOne(id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean result = service.delete(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<Page<NewsDto>> getAll(@RequestBody SearchDto dto) {
        Page<NewsDto> result = service.getAll(dto);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
