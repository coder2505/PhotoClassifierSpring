package com.example.demo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.demo.service.UploadToPythonMicroservice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.CloudinaryServices;
import com.example.demo.service.QueryVectorService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/api/images")
public class ImagesUpload {

    private final CloudinaryServices cloudinaryService;
    private final com.example.demo.service.PhotoFaceService photoFaceService;
    private final QueryVectorService queryVectorService;
    private final UploadToPythonMicroservice uploadToPythonMicroservice;

    public ImagesUpload(CloudinaryServices cloudinaryService, com.example.demo.service.PhotoFaceService photoFaceService, QueryVectorService queryVectorService, UploadToPythonMicroservice uploadToPythonMicroservice) {
        this.cloudinaryService = cloudinaryService;
        this.photoFaceService = photoFaceService;
        this.queryVectorService = queryVectorService;
        this.uploadToPythonMicroservice = uploadToPythonMicroservice;
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
                String url = (String) data.get("url");

                    photoFaceService.saveToPinecone(url);

                    answers.add(data);


            }

            return ResponseEntity.ok(answers);
          
        } catch (Exception e) {
            return ResponseEntity.status(500).body(List.of(Map.of("error", e.getMessage())));
        }
    }

    @PostMapping("/findFaces")
    public ResponseEntity<List<String>> findFaces(@RequestParam("file") MultipartFile facePhoto) throws IOException {

        if (facePhoto.isEmpty()) {
            return ResponseEntity.badRequest().body(List.of("Bad Request, upload one photo"));
        }

        List<Float> embedding = uploadToPythonMicroservice.getImageEmbedding(facePhoto);
        List<String> resp =  queryVectorService.Query(embedding);

        return ResponseEntity.ok(resp);

    }

}
