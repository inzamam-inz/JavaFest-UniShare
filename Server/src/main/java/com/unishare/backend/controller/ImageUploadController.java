package com.unishare.backend.controller;

import com.unishare.backend.service.CloudinaryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class ImageUploadController {
    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    @PostMapping("/image")
    public ResponseEntity <Map> uploadImageToCloudinary(@RequestParam("image")MultipartFile file) {
        Map response = this.cloudinaryImageService.imageUpload(file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
