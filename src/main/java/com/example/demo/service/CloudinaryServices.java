package com.example.demo.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryServices {

    private final Cloudinary cloudinary;

    public CloudinaryServices(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }


    @Async
    public CompletableFuture<Map> upload(MultipartFile file) throws IOException {

        Map<?,?> ans = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
            "folder", "my_app_uploads",
            "resource_type", "auto"
        ));

        return CompletableFuture.completedFuture(ans);
    }
    
}
