package com.example.demo.entities.postgres_tables;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomMemberKey implements Serializable {

    private UUID room_id;
    private UUID user_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomMemberKey that = (RoomMemberKey) o;
        return Objects.equals(user_id, that.user_id) && Objects.equals(room_id, that.room_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, room_id);
    }
}
