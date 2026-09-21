package com.rota.facil.places.persistence.mappers;

import com.rota.facil.places.domain.UpdateBoardPointData;
import com.rota.facil.places.http.dto.request.boardpoints.CreateBoardPointRequest;
import com.rota.facil.places.http.dto.request.boardpoints.UpdateBoardPointRequest;
import com.rota.facil.places.http.dto.response.boardpoints.CreateBoardPointResponse;
import com.rota.facil.places.http.dto.response.boardpoints.BoardPointResponse;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardPointMapper {
    BoardPointEntity map(CreateBoardPointRequest request);
    UpdateBoardPointData map(UpdateBoardPointRequest request);
    CreateBoardPointResponse map(BoardPointEntity entity);
    BoardPointResponse mapToResponse(BoardPointEntity entity);
}
