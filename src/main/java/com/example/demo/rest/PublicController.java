package com.example.demo.rest;

import com.example.demo.Constants;
import com.example.demo.dto.*;
import com.example.demo.exception.AppException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserInfo;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.SignUpRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ColorService;
import com.example.demo.service.NewsService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("public/api/")
public class PublicController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ColorService colorService;

    @Autowired
    NewsService newsService;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("category/all")
    public ResponseEntity<Page<CategoryDto>> getCategory(@RequestBody SearchDto dto) {
        Page<CategoryDto> result = categoryService.searchByPage(dto);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("category/get-all")
    public ResponseEntity<List<CategoryDto>> getCategoryNoParent(@RequestBody SearchDto dto) {
        List<CategoryDto> result = categoryService.getByParentId(null);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


    @PostMapping("product/all")
    public ResponseEntity<List<ProductDto>> getProducts(@RequestBody SearchProductDto dto) {
        List<ProductDto> result = productService.getByCategory(dto.getCategory().getId());
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("product/get-all")
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestBody SearchProductDto dto) {
        List<ProductDto> result = productService.getAll(dto).getContent();
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Long id) {
        ProductDto result = productService.getOneByUser(id);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("color/get-all")
    public ResponseEntity<List<ColorDto>> getAllColors(@RequestBody SearchProductDto dto) {
        List<ColorDto> result = colorService.getAll(dto).getContent();
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("news/get-all")
    public ResponseEntity<List<NewsDto>> getAllNews(@RequestBody SearchDto dto) {
        List<NewsDto> result = newsService.getAll(dto).getContent();
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("news/{id}")
    public ResponseEntity<NewsDto> getNewByID(@PathVariable Long id) {
        NewsDto result = newsService.getOne(id);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), 1);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(Constants.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(signUpRequest.getUserInfo().getFullName());
        userInfo.setPhoneNumber(signUpRequest.getUserInfo().getPhoneNumber());
        userInfo.setAddress(signUpRequest.getUserInfo().getAddress());
        userInfo = userInfoRepository.save(userInfo);

        user.setUserInfo(userInfo);

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @GetMapping("/get-product/{id}")
    public ResponseEntity<List<ProductDto>> getAllParent(@PathVariable Long id) {
        List<ProductDto> result = null;
        if (id != null) {
            result = productService.getByParentId(id);
        }
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
