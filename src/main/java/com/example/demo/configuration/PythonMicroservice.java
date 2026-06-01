package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PythonMicroservice {

    @Bean
    public WebClient webClient(){
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8000").build();
        return webClient;
    }
    
}
