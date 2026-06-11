package com.example.demo.service;


import org.springframework.stereotype.Service;

import com.example.demo.configuration.PythonMicroservice;
import com.example.demo.entities.HasFacesResponse;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PhotoFaceService {

    private final PythonMicroservice pythonMicroservice;
    private final ImageVectorEmbedding imageVectorEmbedding;
    private final SaveToPinecone saveToPinecone;

    public PhotoFaceService(ImageVectorEmbedding imageVectorEmbedding, PythonMicroservice pythonMicroservice, SaveToPinecone saveToPinecone) {
        this.imageVectorEmbedding = imageVectorEmbedding;
        this.pythonMicroservice = pythonMicroservice;
        this.saveToPinecone = saveToPinecone;
    }


    public boolean HasFaces(String imageUrl) {
        // Retrieve has-faces status using WebClient
        HasFacesResponse hasFacesResponse = pythonMicroservice.webClient().get()
                .uri(uriBuilder -> uriBuilder
                        .path("/has-faces")
                        .queryParam("url", imageUrl)
                        .build())
                .retrieve()
                .bodyToMono(HasFacesResponse.class)
                .block();

        if (hasFacesResponse != null) {
            return hasFacesResponse.isHas_faces();
        }

        return false;

    }

    public void saveToPinecone(String imageUrl) {
        String photoId = extractPhotoIdFromUrl(imageUrl);
        saveToPinecone.SaveImageEmbedding(imageVectorEmbedding.getImageEmbeddings(imageUrl), photoId);
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
