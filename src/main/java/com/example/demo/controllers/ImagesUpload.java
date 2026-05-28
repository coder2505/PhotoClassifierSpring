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

import com.example.demo.service.CloudinaryServices;


@RestController
@RequestMapping("/api/images")
public class ImagesUpload {

    private final CloudinaryServices cloudinaryService;

    public ImagesUpload(CloudinaryServices cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

  @PostMapping("/upload")
    public List<ResponseEntity<Map<?,?>>> uploadImage(@RequestParam("file") MultipartFile[] files) {

        List<ResponseEntity<Map<?,?>>> answers = new ArrayList<>();

        if (files.length == 0) {
            answers.add(ResponseEntity.badRequest().body(Map.of("error","Please select a file to upload.")));
            return answers;
        }
        
        try {

            for(MultipartFile e : files) {

                Map<?,?> data = cloudinaryService.upload(e);
                answers.add(ResponseEntity.ok(data));

            }

            return answers;
          
        } catch (Exception e) {
            
            answers.add(ResponseEntity.status(500).body(Map.of("error", e.getMessage())));
            return answers;
        }
    }
    
    
}
