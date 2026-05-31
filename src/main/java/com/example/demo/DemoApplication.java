package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;
import io.pinecone.clients.Pinecone;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		
	    Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();


        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });

        Pinecone pc = new Pinecone.Builder(dotenv.get("PINECONE_API_KEY")).build();

		SpringApplication.run(DemoApplication.class, args);
	}

}
