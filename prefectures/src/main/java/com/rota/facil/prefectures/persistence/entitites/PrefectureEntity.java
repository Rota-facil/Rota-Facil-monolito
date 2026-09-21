package com.rota.facil.prefectures.persistence.entitites;

import com.rota.facil.prefectures.domain.Region;
import com.rota.facil.prefectures.domain.UpdatePrefectureData;
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

    @Builder.Default
    private Boolean active = true;

    public void update(UpdatePrefectureData data) {
        if (data.name() != null) this.name = data.name();
        if (data.region() != null) this.region = data.region();
    }

    public void deactivate() {
        this.active = false;
    }
}
