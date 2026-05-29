package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.PhotoEntity;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity, String>{
    
}
