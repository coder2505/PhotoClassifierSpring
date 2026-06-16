package com.example.demo.controllers;


import com.example.demo.entities.postgres_tables.RoomEntity;
import com.example.demo.entities.postgres_tables.RoomMember;
import com.example.demo.entities.postgres_tables.RoomMemberKey;
import com.example.demo.entities.postgres_tables.UserEntity;
import com.example.demo.repository.RoomMemberRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.java.Log;
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

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;

    CreateControllers(UserRepository userRepository, RoomRepository roomRepository, RoomMemberRepository roomMemberRepository){
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.roomMemberRepository = roomMemberRepository;
    }

    @PostMapping("/user/{username}")
    public ResponseEntity<Map<String, UUID>> createUser(@PathVariable String username){

        UserEntity user = userRepository.save(UserEntity.builder().user_name(username).build());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("user_id", user.getUser_id()));

    }

    @PostMapping("/room/{admin_id}")
    public ResponseEntity<Map<String, UUID>> createRoom(@PathVariable UUID admin_id){

        RoomEntity room = roomRepository.save(new RoomEntity());
        RoomMember roomMember = roomMemberRepository.save(new RoomMember(RoomMemberKey.builder().room_id(room.getRoom_id()).user_id(admin_id).build()));
        log.info(roomMember.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("room_id", room.getRoom_id()));

    }


}
