package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import io.pinecone.unsigned_indices_model.ScoredVectorWithUnsignedIndices;
import org.springframework.stereotype.Service;

import io.pinecone.clients.Index;

@Service
public class QueryVectorService {

    public QueryVectorService(io.pinecone.clients.Index pineconeIndex) {
        this.pineconeIndex = pineconeIndex;
    }

    private final Index pineconeIndex;

    public List<String> Query(List<Float> vector) {
        QueryResponseWithUnsignedIndices q = pineconeIndex.queryByVector(10, vector);

        return q.getMatchesList().stream().filter(match -> match.getScore() >= 0.5)
                .map(ScoredVectorWithUnsignedIndices::getId)
                .collect(Collectors.toList());
    }

}
