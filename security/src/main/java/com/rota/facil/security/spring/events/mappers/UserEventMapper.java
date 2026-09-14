package com.rota.facil.security.spring.events.mappers;

import com.rota.facil.security.spring.events.dto.receive.CreateDefaultAdminUserEventReceive;
import com.rota.facil.users.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEventMapper {
    UserEntity map(CreateDefaultAdminUserEventReceive event);
}
