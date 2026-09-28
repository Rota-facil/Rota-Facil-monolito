package com.rota.facil.journey.persistence.entities;

import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@Builder
@Table(name = "routes_institutions_tb")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionRouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "institution_route_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private InstitutionEntity institution;

    @ManyToOne
    @JoinColumn(name = "route_iid")
    private RouteEntity route;

    @Column(name = "institution_time_going")
    private LocalTime institutionTimeGoing;

    @Column(name = "institution_time_finish")
    private LocalTime institutionTimeFinish;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardPointRouteEntity that)) return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
