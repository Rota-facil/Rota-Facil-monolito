package com.rota.facil.places.persistence.entitites;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "board_points_tb")
public class BoardPointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "board_point_id")
    private UUID id;

    private String name;

    private Double latitude;

    private Double longitude;

    @Builder.Default
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public void update(BoardPointEntity infoToUpdate) {
        if (infoToUpdate.getName() != null) this.name = infoToUpdate.getName();
        if (infoToUpdate.getLatitude() != null) this.latitude = infoToUpdate.getLatitude();
        if (infoToUpdate.getLongitude() != null) this.longitude = infoToUpdate.getLongitude();
    }
}


