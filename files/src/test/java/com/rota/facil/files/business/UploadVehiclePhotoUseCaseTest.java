package com.rota.facil.files.business;

import com.rota.facil.files.business.helpers.ValidateImageHelper;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.business.helpers.FindVehicleByIdHelper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UploadVehiclePhotoUseCaseTest {
    @Mock FindVehicleByIdHelper findVehicleByIdHelper;
    @Mock FileRepository fileRepository;
    @Mock FileStorage fileStorage;
    @InjectMocks UploadVehiclePhotoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UploadVehiclePhotoUseCase(
                findVehicleByIdHelper, new ValidateImageHelper(), fileRepository, fileStorage);
    }

    @Test
    void shouldUploadPhotoForVehicleFromCurrentPrefecture() {
        UUID vehicleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID prefectureId = UUID.randomUUID();
        UserEntity currentUser = UserEntity.builder().id(userId).prefectureId(prefectureId).build();
        MockMultipartFile file = new MockMultipartFile("file", "bus.png", "image/png", new byte[]{1});
        ArgumentCaptor<FileEntity> entityCaptor = ArgumentCaptor.forClass(FileEntity.class);
        when(fileRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(fileStorage.createTemporaryUrl(anyString())).thenReturn("http://minio/photo");

        var response = useCase.execute(vehicleId, file, currentUser);

        verify(findVehicleByIdHelper).execute(vehicleId, prefectureId);
        verify(fileRepository).save(entityCaptor.capture());
        verify(fileStorage).upload(eq(file), eq(entityCaptor.getValue().getObjectKey()));
        assertEquals(vehicleId, entityCaptor.getValue().getOwnerId());
        assertEquals(prefectureId, entityCaptor.getValue().getPrefectureId());
        assertEquals("http://minio/photo", response.url());
    }
}
