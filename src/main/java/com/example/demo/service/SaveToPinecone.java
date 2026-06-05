package com.example.demo.service;
import java.util.List;

import org.springframework.stereotype.Service;

import io.pinecone.clients.Index;
import static io.pinecone.commons.IndexInterface.buildUpsertVectorWithUnsignedIndices;
import io.pinecone.unsigned_indices_model.VectorWithUnsignedIndices;

@Service
public class SaveToPinecone {

    private final Index pineconeIndex;

    public SaveToPinecone(Index pineconeIndex){
        this.pineconeIndex = pineconeIndex;
    }

    public void SaveImageEmbedding(List<Float> embedding, String id){

        VectorWithUnsignedIndices vectorToUpsert = buildUpsertVectorWithUnsignedIndices(
        id,       // vector ID
        embedding,     // use the actual embedding, not hardcoded values
        null,          // List<Long> sparseIndices
        null,          // List<Float> sparseValues
        null           // Struct metadata
    );

    pineconeIndex.upsert(List.of(vectorToUpsert), "");
  

    }
    
    
}
