package com.rota.facil.prefectures.http.controllers;

import com.rota.facil.prefectures.business.prefectures.CreatePrefectureUseCase;
import com.rota.facil.prefectures.http.dto.request.prefecture.CreatePrefectureRequest;
import com.rota.facil.prefectures.http.dto.response.prefecture.CreatePrefectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prefectures")
public class PrefectureController {
    private final CreatePrefectureUseCase createPrefectureUseCase;

    public ResponseEntity<CreatePrefectureResponse> createPrefecture(@RequestBody CreatePrefectureRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createPrefectureUseCase.execute(request));
    }
}
