package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.demo.configuration.PythonMicroservice;
import com.example.demo.entities.ImagesEmbedding;

import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
public class ImageVectorEmbedding {

    private final PythonMicroservice pythonMicroservice;

    public ImageVectorEmbedding(PythonMicroservice pythonMicroservice) {
        this.pythonMicroservice = pythonMicroservice;
    }

    public ArrayList<Float> getImageEmbeddings(String imageUrl) {

        ImagesEmbedding i = pythonMicroservice.webClient().post().uri(uriBuilder -> uriBuilder
                .path("/embedding")
                .build()).bodyValue(new RequestBody(imageUrl)).retrieve().bodyToMono(ImagesEmbedding.class)
                .onErrorResume(e -> Mono.empty()).block();

        if (i==null ||  i.getEmbedding().isEmpty() || i.getEmbedding() == null) {
            return null;
        }

        return i.getEmbedding();

    }

}

@Data
@AllArgsConstructor
class RequestBody {

    String url;

}
