package com.rota.facil.prefectures.repositories;

import com.rota.facil.prefectures.entitites.PrefectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrefectureRepository extends JpaRepository<PrefectureEntity, UUID> {
}
