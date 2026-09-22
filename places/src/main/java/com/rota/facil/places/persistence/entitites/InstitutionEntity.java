package com.rota.facil.places.persistence.entitites;

import com.rota.facil.places.domain.UpdateInstitutionData;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Table(name = "institutions_tb")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "institution_id")
    private UUID id;

    @Column(name = "prefecture_id")
    private UUID prefectureId;

    private String name;

    private Double latitude;

    private Double longitude;

    private Point geom;

    @Builder.Default
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private Boolean active = true;

    public void update(UpdateInstitutionData data) {
        if (data.name() != null) this.name = data.name();
        if (data.latitude() != null) this.latitude = data.latitude();
        if (data.longitude() != null) this.longitude = data.longitude();
    }

    public void deactivate() {
        this.active = false;
    }
}
