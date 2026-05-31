package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PhotoEntity{

    @Id
    String photoid;
    String username;
    boolean has_faces;
}