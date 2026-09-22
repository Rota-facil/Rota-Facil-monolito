package com.rota.facil.journey.persistence.entities;

import com.rota.facil.journey.domain.Presence;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Entity
@Table(name = "trips_users_tb")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "trip_user_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private InstitutionEntity institution;

    @ManyToOne
    @JoinColumn(name = "board_point_id")
    private BoardPointEntity boardPointEntity;

    @Builder.Default
    private Double score = 0.0;

    @Builder.Default
    private Presence presence = Presence.PENDING;

    private boolean return_ = false;

    private boolean going = true;

}
