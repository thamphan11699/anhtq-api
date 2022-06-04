package com.example.demo.service;

import com.example.demo.dto.ImageDto;
import com.example.demo.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<Image> save(MultipartFile[] files);
}
