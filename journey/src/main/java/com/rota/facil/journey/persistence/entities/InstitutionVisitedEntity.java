package com.rota.facil.journey.persistence.entities;

import com.rota.facil.journey.domain.TripOrientation;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Table(name = "institutions_visiteds_tb")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionVisitedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "institution_visited_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private TripEntity trip;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private InstitutionEntity institution;

    private boolean going = false;

    @Column(name = "return")
    private boolean return_ = false;
}
