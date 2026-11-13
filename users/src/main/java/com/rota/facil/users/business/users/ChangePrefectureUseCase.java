package com.rota.facil.users.business.users;

import com.rota.facil.prefectures.business.helpers.FetchPrefectureByIdHelper;
import com.rota.facil.prefectures.http.dto.response.prefecture.PrefectureResponse;
import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.users.http.dto.response.users.UserResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.mappers.UserMapper;
import com.rota.facil.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangePrefectureUseCase {
    private final FetchPrefectureByIdHelper fetchPrefectureByIdHelper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserResponse execute(UUID prefectureId, UserEntity currentUser) {
        PrefectureEntity prefectureFound = this.fetchPrefectureByIdHelper.execute(prefectureId);

        currentUser.setPrefecture(prefectureFound);

        return this.userMapper.map(
                this.userRepository.save(currentUser)
        );
    }
}
