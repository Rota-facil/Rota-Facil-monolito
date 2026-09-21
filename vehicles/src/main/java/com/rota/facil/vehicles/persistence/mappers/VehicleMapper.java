package com.rota.facil.vehicles.persistence.mappers;

import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.domain.UpdateVehicleData;
import com.rota.facil.vehicles.entities.VehicleEntity;
import com.rota.facil.vehicles.http.dto.request.vehicles.CreateVehicleRequest;
import com.rota.facil.vehicles.http.dto.request.vehicles.UpdateVehicleRequest;
import com.rota.facil.vehicles.http.dto.response.vehicles.CreateVehicleResponse;
import com.rota.facil.vehicles.http.dto.response.vehicles.FetchVehicleResponse;
import com.rota.facil.vehicles.http.dto.response.vehicles.ListVehicleResponse;
import com.rota.facil.vehicles.http.dto.response.vehicles.UpdateVehicleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleEntity map(CreateVehicleRequest request);
    @Mapping(target = "capacity", source = "request.capacity")
    @Mapping(target = "plate", source = "request.plate")
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "driver", source = "driver")
    UpdateVehicleData map(UpdateVehicleRequest request, UserEntity driver);
    CreateVehicleResponse map(VehicleEntity entity);
    FetchVehicleResponse mapToFetchResponse(VehicleEntity entity);
    ListVehicleResponse mapToListResponse(VehicleEntity entity);
    UpdateVehicleResponse mapToUpdateResponse(VehicleEntity entity);
}
