package com.rota.facil.prefectures.business.prefectures;

import com.rota.facil.prefectures.http.dto.response.prefecture.PrefectureResponse;
import com.rota.facil.prefectures.http.mappers.PrefectureMapper;
import com.rota.facil.prefectures.persistence.repositories.PrefectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListPrefecturesUseCase {
    private final PrefectureRepository prefectureRepository;
    private final PrefectureMapper prefectureMapper;

    public List<PrefectureResponse> execute() {
        return prefectureRepository.findAllByActiveTrue()
                .stream()
                .map(prefectureMapper::mapToResponse)
                .toList();
    }
}
