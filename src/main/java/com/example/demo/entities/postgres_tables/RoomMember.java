package com.example.demo.entities.postgres_tables;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "room_members")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomMember{

    @EmbeddedId
    RoomMemberKey roomMemberKey;

}
