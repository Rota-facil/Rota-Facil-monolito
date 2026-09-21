package com.rota.facil.journey.persistence.entities;

import com.rota.facil.journey.domain.Progress;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.vehicles.entities.VehicleEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Table(name = "trips_tb")
@Builder
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "trip_id")
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;

    @Builder.Default
    private Long students = 0L;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private RouteEntity route;

    @Column(name = "reason_of_cancellation")
    private String reasonOfCancellation;

    @Enumerated(EnumType.STRING)
    @Column(name = "actual_status")
    private Progress actualStatus;

    @Builder.Default
    private Double latitude = 0.0;

    @Builder.Default
    private Double longitude = 0.0;

    @Column(name = "prefecture_id")
    private UUID prefectureId;

    @Builder.Default
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<TripStatusEntity> tripStatus;

    @ManyToMany
    @JoinTable(
            name = "ignored_institutions_tb",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "institution_id")
    )
    private Set<InstitutionEntity> ignoredInstitutions;

    @ManyToMany
    @JoinTable(
            name = "ignored_board_points_tb",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "board_point_id")
    )
    private Set<BoardPointEntity> ignoredBoardPoints;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripEntity that)) return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void increaseStudents() {
        this.students++;
    }

    public void decreaseStudents() {
        this.students--;
    }

    public void updateCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
