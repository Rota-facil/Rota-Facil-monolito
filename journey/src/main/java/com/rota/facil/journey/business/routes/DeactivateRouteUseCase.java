package com.rota.facil.journey.business.routes;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.journey.business.helpers.routes.FindRouteByIdAndPrefectureIdHelper;
import com.rota.facil.journey.business.helpers.routes.ValidateUpdateRouteHelper;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.journey.persistence.repositories.RouteRecurringRepository;
import com.rota.facil.journey.persistence.repositories.RouteRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class DeactivateRouteUseCase {
    private final RouteRecurringRepository routeRecurringRepository;
    private final ValidateUpdateRouteHelper validateUpdateRouteHelper;
    private final FindRouteByIdAndPrefectureIdHelper findRouteByIdAndPrefectureIdHelper;
    private final RouteRepository routeRepository;

    @Transactional
    public void execute(UserEntity currentUser, UUID routeId) {
        RouteEntity routeFound = this.findRouteByIdAndPrefectureIdHelper.execute(routeId, currentUser.getPrefectureId());

        this.validateUpdateRouteHelper.execute(routeId);

        routeFound.setActive(false);

        this.routeRecurringRepository.deleteAllByRouteId(routeId);

        this.routeRepository.save(routeFound);
    }
}
