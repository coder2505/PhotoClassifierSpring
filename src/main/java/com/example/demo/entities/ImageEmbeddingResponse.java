package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ImageEmbeddingResponse {

    List<Float> embedding;
    
}
