package com.rota.facil.files.business.useCases.routes;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.entities.RouteFilesEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.journey.business.helpers.routes.FindRouteByIdHelper;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UploadHeatMapRouteUseCase {
    private final FileStorage fileStorage;
    private final FileMapper fileMapper;
    private final FileRepository fileRepository;
    private final FindRouteByIdHelper findRouteByIdHelper;

    public void execute(UUID routeId, MultipartFile file, UserEntity currentUser) {
        RouteEntity routeFound = this.findRouteByIdHelper.execute(routeId);

        RouteFilesEntity heatMapRoute = RouteFilesEntity.newFile(
                currentUser,
                currentUser.getPrefecture(),
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize(),
                FileCategory.HEAT_MAP,
                routeFound
        );

        FileEntity saved = this.fileRepository.save(heatMapRoute);

        this.fileStorage.upload(file, saved.getObjectKey());

        this.fileMapper.map(saved);
    }
}
