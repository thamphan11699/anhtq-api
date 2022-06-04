package com.example.demo.rest;

import com.example.demo.Constants;
import com.example.demo.dto.SearchDto;
import com.example.demo.dto.UserDto;
import com.example.demo.security.CurrentUser;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/me")
    @Secured({Constants.ROLE_ADMIN, Constants.ROLE_USER})
    public UserDto getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserDto userDto = userService.getOne(currentUser.getId());
        return userDto;
    }

    @PostMapping()
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<UserDto> save(@RequestBody UserDto dto) {
        UserDto result = userService.saveOrUpdate(dto, null);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN, Constants.ROLE_USER})
    public ResponseEntity<UserDto> update(@RequestBody UserDto dto, @PathVariable Long id) {
        UserDto result = userService.saveOrUpdate(dto, id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        UserDto result = userService.getOne(id);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean result = userService.delete(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @PostMapping("/all")
    @Secured({Constants.ROLE_ADMIN})
    public ResponseEntity<Page<UserDto>> getAll(@RequestBody SearchDto dto) {
        Page<UserDto> result = userService.getAll(dto);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
