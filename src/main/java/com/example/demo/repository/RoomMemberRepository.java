package com.example.demo.repository;

import com.example.demo.entities.postgres_tables.RoomMember;
import com.example.demo.entities.postgres_tables.RoomMemberKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomMemberRepository extends JpaRepository<RoomMember, RoomMemberKey> {
}
