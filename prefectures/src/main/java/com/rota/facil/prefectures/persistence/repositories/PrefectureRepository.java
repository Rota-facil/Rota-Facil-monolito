package com.rota.facil.prefectures.persistence.repositories;

import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrefectureRepository extends JpaRepository<PrefectureEntity, UUID> {
}
