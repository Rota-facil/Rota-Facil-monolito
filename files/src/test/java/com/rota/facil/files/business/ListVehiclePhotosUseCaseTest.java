package com.rota.facil.files.business;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.http.mappers.FileResponseMapper;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.business.helpers.FindVehicleByIdHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListVehiclePhotosUseCaseTest {
    @Mock FindVehicleByIdHelper findVehicleByIdHelper;
    @Mock FileRepository fileRepository;
    @Mock FileStorage fileStorage;

    @Test
    void shouldListOnlyVehiclePhotosFromCurrentPrefecture() {
        UUID vehicleId = UUID.randomUUID();
        UUID prefectureId = UUID.randomUUID();
        UserEntity currentUser = UserEntity.builder().prefectureId(prefectureId).build();
        FileEntity photo = FileEntity.newVehiclePhoto(
                vehicleId, UUID.randomUUID(), prefectureId, "vehicle.png", "image/png", 1L);
        when(fileRepository.findAllByOwnerIdAndPrefectureIdAndFileCategoryOrderByCreatedAtDesc(
                vehicleId, prefectureId, FileCategory.VEHICLE_PHOTO)).thenReturn(List.of(photo));
        when(fileStorage.createTemporaryUrl(photo.getObjectKey())).thenReturn("http://minio/photo");
        var useCase = new ListVehiclePhotosUseCase(
                findVehicleByIdHelper, fileRepository, new FileResponseMapper(fileStorage));

        var response = useCase.execute(vehicleId, currentUser);

        verify(findVehicleByIdHelper).execute(vehicleId, prefectureId);
        assertEquals(1, response.size());
        assertEquals("http://minio/photo", response.getFirst().url());
    }
}
