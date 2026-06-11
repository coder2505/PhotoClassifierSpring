package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.configuration.PythonMicroservice;
import com.example.demo.entities.ImageEmbeddingResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

@Slf4j
@Service
public class UploadToPythonMicroservice {

    private final PythonMicroservice pythonMicroservice;

    public UploadToPythonMicroservice(PythonMicroservice pythonMicroservice) {
        this.pythonMicroservice = pythonMicroservice;
    }


    public List<Float> getImageEmbedding(MultipartFile image) throws IOException {

        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        byte[] imageBytes = image.getBytes();

        builder.part("file", new ByteArrayResource(imageBytes) {
            @Override
            public @Nullable String getFilename() {
                return "image.jpg";
            }
        }).contentType(MediaType.IMAGE_JPEG);

        ImageEmbeddingResponse imageEmbeddingResponse = pythonMicroservice.webClient().post()
                .uri(uriBuilder -> uriBuilder
                        .path("/bytesToEmbedding")
                        .build()).contentType(MediaType.MULTIPART_FORM_DATA).
                body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(ImageEmbeddingResponse.class)
                .block();


        assert imageEmbeddingResponse != null;
        return imageEmbeddingResponse.getEmbedding();

    }


}
