package com.rota.facil.journey.persistence.entities;

import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Table(name = "board_points_visiteds_tb")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardPointVisitedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "board_point_visited_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private TripEntity trip;

    @ManyToOne
    @JoinColumn(name = "board_point_id")
    private BoardPointEntity boardPoint;

    private boolean going = false;

    private boolean return_ = false;
}
