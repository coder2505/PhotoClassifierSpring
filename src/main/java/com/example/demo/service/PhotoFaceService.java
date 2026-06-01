package com.example.demo.service;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.configuration.PythonMicroservice;
import com.example.demo.entities.HasFacesResponse;
import com.example.demo.entities.PhotoEntity;
import com.example.demo.repository.PhotoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PhotoFaceService {

    private final PythonMicroservice pythonMicroservice;
    private final PhotoRepository photoRepository;
    private final ImageVectorEmbedding imageVectorEmbedding;

    public PhotoFaceService(PhotoRepository photoRepository, ImageVectorEmbedding imageVectorEmbedding, PythonMicroservice pythonMicroservice) {
        this.photoRepository = photoRepository;
        this.imageVectorEmbedding = imageVectorEmbedding;
        this.pythonMicroservice = pythonMicroservice;
    }

    /**
     * Service method to check if the image has faces and save the entity to the DB.
     * Only input is the imageUrl.
     */
    public void processAndSave(String imageUrl) {
        // Retrieve has-faces status using WebClient
        HasFacesResponse hasFacesResponse = pythonMicroservice.webClient().get()
                .uri(uriBuilder -> uriBuilder
                        .path("/has-faces")
                        .queryParam("url", imageUrl)
                        .build())
                .retrieve()
                .bodyToMono(HasFacesResponse.class)
                .block();

        boolean hasFaces = hasFacesResponse != null ? hasFacesResponse.isHas_faces() : false;

        // Parse photoid (display_name/filename) from the Cloudinary URL
        String photoId = extractPhotoIdFromUrl(imageUrl);

        // Populate and save entity to database
        PhotoEntity photo = new PhotoEntity();
        photo.setPhotoid(photoId);
        photo.setUsername(null);
        photo.setHas_faces(hasFaces);

        photoRepository.save(photo);

           if(hasFaces){

            log.info(imageVectorEmbedding.getImageEmbeddings(imageUrl).toString());

            }
    }

    private String extractPhotoIdFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return "unknown_" + System.currentTimeMillis();
        }
        try {
            String filename = url.substring(url.lastIndexOf('/') + 1);
            if (filename.contains(".")) {
                filename = filename.substring(0, filename.lastIndexOf('.'));
            }
            return filename;
        } catch (Exception e) {
            return "unknown_" + System.currentTimeMillis();
        }
    }
}
