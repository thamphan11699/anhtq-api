package com.example.demo.service.imp;

import com.example.demo.dto.UserDto;
import com.example.demo.model.Image;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.FileService;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImp implements FileService {

    @Value("${app.location.file}")
    private String fileDir;

    @Value("${localhost.path}")
    private String hostPath;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ImageService imageService;

    @Override
    public UserDto uploadAvatar(MultipartFile maMultipartFile, Long id) {
        try {
            String absolutePath = fileDir + "/";
//            String fileName = maMultipartFile.getOriginalFilename().split("\\.(?=[^\\.]+$)")[0];
            String extension = maMultipartFile.getOriginalFilename().split("\\.(?=[^\\.]+$)")[1];
            UUID fileNameImage = UUID.randomUUID();
            try {
                File fileToBeSaved = new File(absolutePath, fileNameImage + "." + extension);
                maMultipartFile.transferTo(fileToBeSaved);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            String mainImageUrl = this.hostPath + "public/" + fileNameImage + "." + extension;
            User user = userRepository.getById(id);
            user.setAvatar(mainImageUrl);
            UserDto dto = new UserDto(user);
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        try {
            String absolutePath = fileDir + "/";
            String extension = multipartFile.getOriginalFilename().split("\\.(?=[^\\.]+$)")[1];
            UUID fileNameImage = UUID.randomUUID();
            try {
                File fileToBeSaved = new File(absolutePath, fileNameImage + "." + extension);
                multipartFile.transferTo(fileToBeSaved);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            String mainImageUrl = this.hostPath + "public/" + fileNameImage + "." + extension;
            return mainImageUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Image> uploadFiles(MultipartFile[] multipartFile) {
        try {
            return imageService.save(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
