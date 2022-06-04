package com.example.demo.rest;


import com.example.demo.Constants;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Image;
import com.example.demo.service.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @Value("${app.location.file}")
    private String fileDir;

    @PostMapping("/api/file/{id}")
    public ResponseEntity<UserDto> uploadAvatar(@RequestParam("file")MultipartFile file, @PathVariable("id") Long id) {
        UserDto result = fileService.uploadAvatar(file, id);
        return new  ResponseEntity<UserDto>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/public/{filename}", method = RequestMethod.GET)
    public void getImage(HttpServletResponse response, @PathVariable String filename) throws IOException {
        File file = new File(fileDir+filename);
        if(file.exists()) {
            String contentType = "application/octet-stream";
            response.setContentType(contentType);
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(file);
            // copy from in to out
            IOUtils.copy(in, out);
            out.close();
            in.close();
        }else {
            throw new FileNotFoundException();
        }
    }

    @PostMapping("api/upload-file")
    @Secured({Constants.ROLE_ADMIN, Constants.ROLE_USER})
    public ResponseEntity<String> updateThumbnail(@RequestParam("file")MultipartFile file) {
        String result = fileService.uploadFile(file);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("api/upload-files")
    @Secured({Constants.ROLE_ADMIN, Constants.ROLE_USER})
    public ResponseEntity<List<Image>> updateFiles(@RequestParam("files")MultipartFile[] files) {
        List<Image> result = fileService.uploadFiles(files);
        return new ResponseEntity<>(result, result != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
