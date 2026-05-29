package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entities.PhotoEntity;
import com.example.demo.repository.PhotoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class InsertPhotoIdDB {

    private final PhotoRepository photoRepository;

    public void insertPhotoId(PhotoEntity photoEntity){

        photoRepository.save(photoEntity);

    }
    
}
