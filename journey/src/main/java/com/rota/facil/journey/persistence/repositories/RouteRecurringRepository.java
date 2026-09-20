package com.rota.facil.journey.persistence.repositories;

import com.rota.facil.journey.persistence.entities.RouteRecurringEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RouteRecurringRepository extends JpaRepository<RouteRecurringEntity, UUID> {
}
