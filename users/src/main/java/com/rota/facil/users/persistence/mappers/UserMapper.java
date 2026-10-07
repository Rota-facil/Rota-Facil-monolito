package com.rota.facil.users.persistence.mappers;

import com.rota.facil.users.http.dto.request.users.CreateDriverRequest;
import com.rota.facil.users.http.dto.response.users.UserResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity map(CreateDriverRequest request);
    UserResponse map(UserEntity entity);
}
