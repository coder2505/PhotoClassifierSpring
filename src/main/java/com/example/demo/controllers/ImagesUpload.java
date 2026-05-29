package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.PhotoEntity;
import com.example.demo.service.CloudinaryServices;
import com.example.demo.service.InsertPhotoIdDB;


@RestController
@RequestMapping("/api/images")
public class ImagesUpload {

    private final CloudinaryServices cloudinaryService;
    private final InsertPhotoIdDB insertPhotoIdDB;

    public ImagesUpload(CloudinaryServices cloudinaryService,InsertPhotoIdDB insertPhotoIdDB) {
        this.cloudinaryService = cloudinaryService;
        this.insertPhotoIdDB = insertPhotoIdDB;
    }

    @PostMapping("/upload")
    public ResponseEntity<List<Map<?,?>>> uploadImage(@RequestParam("file") MultipartFile[] files) {

        List<Map<?,?>> answers = new ArrayList<>();

        if (files.length == 0) {
            return ResponseEntity.badRequest().body(List.of(Map.of("error","Please select a file to upload.")));
        }
        
        try {

            for(MultipartFile e : files) {

                Map<?,?> data = cloudinaryService.upload(e);

                String publicId = (String) data.get("display_name");

                PhotoEntity newPhoto = new PhotoEntity();
                newPhoto.setPhotoid(publicId); // Assuming setId handles the Cloudinary ID
                newPhoto.setUsername(null);   // Explicitly setting name as null as requested
                
                insertPhotoIdDB.insertPhotoId(newPhoto);

                answers.add(data);

            }

            return ResponseEntity.ok(answers);
          
        } catch (Exception e) {
            return ResponseEntity.status(500).body(List.of(Map.of("error", e.getMessage())));
        }
    }
    
    
}
