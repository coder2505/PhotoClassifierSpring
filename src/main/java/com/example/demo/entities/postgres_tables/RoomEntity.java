package com.example.demo.entities.postgres_tables;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Entity
@Table(name = "rooms")
@Data
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID room_id;
}
