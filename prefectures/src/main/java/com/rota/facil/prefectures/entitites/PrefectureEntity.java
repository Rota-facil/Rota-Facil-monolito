package com.rota.facil.prefectures.entitites;

import com.rota.facil.prefectures.domain.Region;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Entity
@Table(name = "prefectures_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrefectureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "prefecture_id")
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Region region;

    public void update(PrefectureEntity infoToUpdate) {
        if (infoToUpdate.getName() != null) this.name = infoToUpdate.getName();
        if (infoToUpdate.getRegion() != null) this.region = infoToUpdate.getRegion();
    }
}