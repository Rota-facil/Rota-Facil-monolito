package com.rota.facil.journey.persistence.entities;

import com.rota.facil.vehicles.entities.VehicleEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Table(name = "route_recurring_tb")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteRecurringEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "route_recurring_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private RouteEntity route;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;
}
