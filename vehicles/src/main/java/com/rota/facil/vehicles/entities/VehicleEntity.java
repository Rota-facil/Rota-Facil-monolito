package com.rota.facil.vehicles.entities;

import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.domain.VehicleStatus;
import com.rota.facil.vehicles.domain.VehicleType;
import com.rota.facil.vehicles.domain.exceptions.VehicleInOperationException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Table(name = "vehicles_tb")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "vehicle_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private UserEntity driver;

    @Column(name = "prefecture_id")
    private UUID prefectureId;

    @Builder.Default
    private Long capacity = 0L;

    private String plate;

    private boolean active = true;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private VehicleStatus status = VehicleStatus.OUT_OF_OPERATION;

    @Column(name = "vehicle_type")
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public void update(Long capacity, String plate, VehicleStatus status, UserEntity driver) {
        if (capacity != null) this.capacity = capacity;
        if (plate != null) this.plate = plate;
        if (status != null) this.status = status;

        UUID currentDriverId = this.driver != null ? this.driver.getId() : null;
        UUID newDriverId = driver != null ? driver.getId() : null;
        boolean driverChanged = !java.util.Objects.equals(currentDriverId, newDriverId);

        if (driverChanged && VehicleStatus.OPERATION.equals(this.status)) {
            throw new VehicleInOperationException();
        }

        this.driver = driver;
    }

    public void deactivate() {
        if (VehicleStatus.OPERATION.equals(this.status)) {
            throw new VehicleInOperationException("Não é possível excluir um veículo enquanto ele está em operação");
        }

        this.driver = null;
        this.active = false;
    }
}

