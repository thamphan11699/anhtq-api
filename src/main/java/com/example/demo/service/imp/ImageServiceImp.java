package com.example.demo.service.imp;

import com.example.demo.dto.ImageDto;
import com.example.demo.model.Image;
import com.example.demo.repository.ImageRepository;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    ImageRepository repository;

    @Value("${app.location.file}")
    private String fileDir;

    @Value("${localhost.path}")
    private String hostPath;

    @Override
    public List<Image> save(MultipartFile[] files) {
        try {
            List<Image> list = new ArrayList<>();
            String absolutePath = fileDir + "/";
            for (MultipartFile file: files) {
                String extension = file.getOriginalFilename().split("\\.(?=[^\\.]+$)")[1];
                UUID fileNameImage = UUID.randomUUID();
                File fileToBeSaved = new File(absolutePath, fileNameImage + "." + extension);
                file.transferTo(fileToBeSaved);
                String url = hostPath + "public/" + fileNameImage + "." + extension;
                Image image = new Image();
                image.setName(fileNameImage.toString());
                image.setUrl(url);
                image.setTitle("Image");
                image = repository.save(image);
                list.add(image);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
