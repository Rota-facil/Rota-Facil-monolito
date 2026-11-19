package com.rota.facil.files.http.dto.request.users;

import java.util.List;
import java.util.UUID;

public record ListProfilePicUsersRequest(
        List<UUID> usersId
) {
}
