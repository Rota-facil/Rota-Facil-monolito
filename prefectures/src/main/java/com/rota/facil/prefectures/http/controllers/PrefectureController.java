package com.rota.facil.prefectures.http.controllers;

import com.rota.facil.prefectures.business.prefectures.CreatePrefectureUseCase;
import com.rota.facil.prefectures.business.prefectures.DeletePrefectureUseCase;
import com.rota.facil.prefectures.business.prefectures.FetchPrefectureUseCase;
import com.rota.facil.prefectures.business.prefectures.ListPrefecturesUseCase;
import com.rota.facil.prefectures.business.prefectures.UpdatePrefectureUseCase;
import com.rota.facil.prefectures.http.dto.request.prefecture.CreatePrefectureRequest;
import com.rota.facil.prefectures.http.dto.request.prefecture.UpdatePrefectureRequest;
import com.rota.facil.prefectures.http.dto.response.prefecture.CreatePrefectureResponse;
import com.rota.facil.prefectures.http.dto.response.prefecture.PrefectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prefectures")
public class PrefectureController {
    private final CreatePrefectureUseCase createPrefectureUseCase;
    private final DeletePrefectureUseCase deletePrefectureUseCase;
    private final FetchPrefectureUseCase fetchPrefectureUseCase;
    private final ListPrefecturesUseCase listPrefecturesUseCase;
    private final UpdatePrefectureUseCase updatePrefectureUseCase;

    @PostMapping
    public ResponseEntity<CreatePrefectureResponse> createPrefecture(@RequestBody CreatePrefectureRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createPrefectureUseCase.execute(request));
    }

    @GetMapping("/{prefectureId}")
    public ResponseEntity<PrefectureResponse> fetchPrefecture(@PathVariable UUID prefectureId) {
        return ResponseEntity.ok(fetchPrefectureUseCase.execute(prefectureId));
    }

    @GetMapping
    public ResponseEntity<List<PrefectureResponse>> listPrefectures() {
        return ResponseEntity.ok(listPrefecturesUseCase.execute());
    }

    @PutMapping("/{prefectureId}")
    public ResponseEntity<PrefectureResponse> updatePrefecture(
            @PathVariable UUID prefectureId,
            @RequestBody UpdatePrefectureRequest request
    ) {
        return ResponseEntity.ok(updatePrefectureUseCase.execute(prefectureId, request));
    }

    @DeleteMapping("/{prefectureId}")
    public ResponseEntity<Void> deletePrefecture(@PathVariable UUID prefectureId) {
        deletePrefectureUseCase.execute(prefectureId);
        return ResponseEntity.ok().build();
    }
}
