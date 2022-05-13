package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    UserDto uploadAvatar(MultipartFile maMultipartFile, Long id);

    String uploadFile(MultipartFile multipartFile);

    List<Image> uploadFiles(MultipartFile[] multipartFile);

}
