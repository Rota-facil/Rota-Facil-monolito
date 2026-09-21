package com.rota.facil.vehicles.persistence.mappers;

import com.rota.facil.vehicles.entities.VehicleEntity;
import com.rota.facil.vehicles.http.dto.request.vehicles.CreateVehicleRequest;
import com.rota.facil.vehicles.http.dto.response.vehicles.CreateVehicleResponse;
import com.rota.facil.vehicles.http.dto.response.vehicles.FetchVehicleResponse;
import com.rota.facil.vehicles.http.dto.response.vehicles.UpdateVehicleResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleEntity map(CreateVehicleRequest request);
    CreateVehicleResponse map(VehicleEntity entity);
    FetchVehicleResponse mapToFetchResponse(VehicleEntity entity);
    UpdateVehicleResponse mapToUpdateResponse(VehicleEntity entity);
}
