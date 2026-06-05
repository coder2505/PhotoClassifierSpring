package com.example.demo.configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.pinecone.clients.Index;
import io.pinecone.clients.Pinecone;



@Configuration
public class PineConeConfig{

    @Value("${PINECONE_API_KEY}")
    private String pineconeApiKey;

    @Bean
    public Pinecone pineconeConfig(){
        Pinecone pc = new Pinecone.Builder(pineconeApiKey).build();
        return pc;
    }

    @Bean
    public Index pineconeIndex(Pinecone pineconeClient) {
        return pineconeClient.getIndexConnection("first");
    }

    
}
