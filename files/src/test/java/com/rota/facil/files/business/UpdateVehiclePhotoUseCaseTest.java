package com.rota.facil.files.business;

import com.rota.facil.files.business.helpers.FindVehiclePhotoByIdHelper;
import com.rota.facil.files.business.helpers.ValidateImageHelper;
import com.rota.facil.files.http.mappers.FileResponseMapper;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.business.helpers.FindVehicleByIdHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateVehiclePhotoUseCaseTest {
    @Mock FindVehicleByIdHelper findVehicleByIdHelper;
    @Mock FindVehiclePhotoByIdHelper findVehiclePhotoByIdHelper;
    @Mock FileRepository fileRepository;
    @Mock FileStorage fileStorage;

    @Test
    void shouldReplaceObjectAndUpdateMetadataThroughEntity() {
        UUID vehicleId = UUID.randomUUID();
        UUID photoId = UUID.randomUUID();
        UUID prefectureId = UUID.randomUUID();
        UserEntity currentUser = UserEntity.builder().prefectureId(prefectureId).build();
        FileEntity photo = FileEntity.newVehiclePhoto(
                vehicleId, UUID.randomUUID(), prefectureId, "old.png", "image/png", 1L);
        String originalObjectKey = photo.getObjectKey();
        MockMultipartFile newPhoto = new MockMultipartFile(
                "file", "new.jpg", "image/jpeg", new byte[]{1, 2});
        when(findVehiclePhotoByIdHelper.execute(photoId, vehicleId, prefectureId)).thenReturn(photo);
        when(fileRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(fileStorage.createTemporaryUrl(originalObjectKey)).thenReturn("http://minio/photo");
        var useCase = new UpdateVehiclePhotoUseCase(
                findVehicleByIdHelper,
                findVehiclePhotoByIdHelper,
                new ValidateImageHelper(),
                fileRepository,
                fileStorage,
                new FileResponseMapper(fileStorage)
        );

        var response = useCase.execute(vehicleId, photoId, newPhoto, currentUser);

        verify(findVehicleByIdHelper).execute(vehicleId, prefectureId);
        verify(fileStorage).upload(newPhoto, originalObjectKey);
        assertEquals(originalObjectKey, photo.getObjectKey());
        assertEquals("new.jpg", photo.getOriginalFilename());
        assertEquals("image/jpeg", photo.getMimeType());
        assertEquals(2L, photo.getFileSizeBytes());
        assertEquals("new.jpg", response.originalFilename());
    }
}
