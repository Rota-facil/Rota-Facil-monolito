package com.rota.facil.journey.persistence.entities;

import com.rota.facil.journey.domain.DaysOfWeek;
import com.rota.facil.journey.domain.Shift;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Table(name = "routes_tb")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "route_id")
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    private LocalTime going;

    @Column(name = "return")
    private LocalTime return_;

    private LocalTime goingFinish;

    private LocalTime returnFinish;

    @Column(name = "prefecture_id")
    private UUID prefectureId;

    @Builder.Default
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ElementCollection
    @CollectionTable(name = "route_recurring_day_of_week_tb", joinColumns = @JoinColumn(name = "route_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "days_of_week")
    private Set<DaysOfWeek> daysOfWeek;

    @ManyToMany
    @JoinTable(
            name = "routes_institutions_tb",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "institution_id")
    )
    private List<InstitutionEntity> institutions;


    @OneToMany(mappedBy = "route")
    private List<BoardPointRouteEntity> boardPoints;

    @Builder.Default
    private Boolean active = true;
}
