package com.rota.facil.journey.persistence.repositories;

import com.rota.facil.journey.domain.DaysOfWeek;
import com.rota.facil.journey.persistence.entities.RouteRecurringEntity;
import com.rota.facil.vehicles.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RouteRecurringRepository extends JpaRepository<RouteRecurringEntity, UUID> {
    @Query("""
        SELECT v FROM RouteRecurringEntity rr
        INNER JOIN rr.vehicle v
        INNER JOIN rr.route r
        WHERE v.id = :vehicleId
        AND r.id = :routeId
    """)
    Optional<VehicleEntity> findVehicleByVehicleIdAndRouteId(@Param(value = "vehicleId") UUID vehicleId, @Param(value = "routeId") UUID routeId);

    @Query("""
        SELECT rr FROM RouteRecurringEntity rr
        INNER JOIN rr.route r
        WHERE :fromValueDay IN r.daysOfWeek
    """)
    List<RouteRecurringEntity> findAllRecurringToday(@Param("fromValueDay") DaysOfWeek fromValueDay);
}
