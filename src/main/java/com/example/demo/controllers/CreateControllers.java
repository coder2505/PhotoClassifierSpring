package com.example.demo.controllers;


import com.example.demo.entities.postgres_tables.UserEntity;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/create")
public class CreateControllers {

    UserRepository userRepository;

    CreateControllers(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/user/{username}")
    public ResponseEntity<Map<String, UUID>> createUser(@PathVariable String username){

        UserEntity user = userRepository.save(UserEntity.builder().user_name(username).build());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("user_id", user.getUser_id()));

    }


}
