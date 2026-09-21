package com.rota.facil.files.business;

import com.rota.facil.files.business.helpers.FindVehiclePhotoByIdHelper;
import com.rota.facil.files.http.mappers.FileResponseMapper;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.business.helpers.FindVehicleByIdHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchVehiclePhotoUseCaseTest {
    @Mock FindVehicleByIdHelper findVehicleByIdHelper;
    @Mock FindVehiclePhotoByIdHelper findVehiclePhotoByIdHelper;
    @Mock FileStorage fileStorage;

    @Test
    void shouldFetchPhotoByVehicleAndCurrentPrefecture() {
        UUID vehicleId = UUID.randomUUID();
        UUID photoId = UUID.randomUUID();
        UUID prefectureId = UUID.randomUUID();
        UserEntity currentUser = UserEntity.builder().prefectureId(prefectureId).build();
        FileEntity photo = FileEntity.newVehiclePhoto(
                vehicleId, UUID.randomUUID(), prefectureId, "vehicle.png", "image/png", 1L);
        when(findVehiclePhotoByIdHelper.execute(photoId, vehicleId, prefectureId)).thenReturn(photo);
        when(fileStorage.createTemporaryUrl(photo.getObjectKey())).thenReturn("http://minio/photo");
        var useCase = new FetchVehiclePhotoUseCase(
                findVehicleByIdHelper,
                findVehiclePhotoByIdHelper,
                new FileResponseMapper(fileStorage)
        );

        var response = useCase.execute(vehicleId, photoId, currentUser);

        verify(findVehicleByIdHelper).execute(vehicleId, prefectureId);
        assertEquals("vehicle.png", response.originalFilename());
        assertEquals("http://minio/photo", response.url());
    }
}
