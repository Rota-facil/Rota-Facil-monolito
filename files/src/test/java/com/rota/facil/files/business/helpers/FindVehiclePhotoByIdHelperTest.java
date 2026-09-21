package com.rota.facil.files.business.helpers;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.exceptions.FileNotFoundException;
import com.rota.facil.files.persistence.repositories.FileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindVehiclePhotoByIdHelperTest {
    @Mock FileRepository fileRepository;

    @Test
    void shouldThrowWhenPhotoDoesNotBelongToVehicleAndPrefecture() {
        UUID photoId = UUID.randomUUID();
        UUID vehicleId = UUID.randomUUID();
        UUID prefectureId = UUID.randomUUID();
        when(fileRepository.findByIdAndOwnerIdAndPrefectureIdAndFileCategory(
                photoId, vehicleId, prefectureId, FileCategory.VEHICLE_PHOTO
        )).thenReturn(Optional.empty());
        var helper = new FindVehiclePhotoByIdHelper(fileRepository);

        assertThrows(FileNotFoundException.class,
                () -> helper.execute(photoId, vehicleId, prefectureId));
    }
}
