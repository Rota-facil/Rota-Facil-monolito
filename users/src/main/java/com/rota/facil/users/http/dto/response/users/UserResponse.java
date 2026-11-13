package com.rota.facil.users.http.dto.response.users;

import com.rota.facil.users.domain.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        UserPrefectureResponse prefecture,
        String name,
        String email,
        String cpf,
        boolean active,
        Long completedTrips,
        Double score,
        Role role,
        LocalDateTime createdAt
) {
}
