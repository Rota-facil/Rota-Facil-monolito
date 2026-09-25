package com.rota.facil.journey.business.trips;

import com.rota.facil.journey.business.helpers.trips.FindTripByIdAndPrefectureHelper;
import com.rota.facil.journey.business.helpers.trips.RegisterIgnoredBoardPointsHelper;
import com.rota.facil.journey.business.helpers.trips.RegisterIgnoredInstitutionsHelper;
import com.rota.facil.journey.domain.Progress;
import com.rota.facil.journey.domain.TripOrientation;
import com.rota.facil.journey.exceptions.TripCannotBeReturnInit;
import com.rota.facil.journey.http.dto.response.trips.TripResponse;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.entities.TripStatusEntity;
import com.rota.facil.journey.persistence.mappers.TripMapper;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ReturnTripUseCase {
    private final FindTripByIdAndPrefectureHelper findTripByIdAndPrefectureHelper;
    private final RegisterIgnoredBoardPointsHelper registerIgnoredBoardPointsHelper;
    private final RegisterIgnoredInstitutionsHelper registerIgnoredInstitutionsHelper;
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    @Transactional
    public TripResponse execute(UserEntity currentUser, UUID tripId) {
        TripEntity tripFound = this.findTripByIdAndPrefectureHelper.execute(tripId, currentUser.getPrefectureId());

        if (!Progress.RETURN_FINISHED.equals(tripFound.getActualStatus())) throw new TripCannotBeReturnInit("Para iniciar a volta, a ida deve ser finalizada");

        tripFound.addNewStatus(Progress.RETURN_STARTED);

        this.registerIgnoredBoardPointsHelper.execute(tripFound, TripOrientation.RETURN);
        this.registerIgnoredInstitutionsHelper.execute(tripFound, TripOrientation.RETURN);

        return this.tripMapper.map(this.tripRepository.save(tripFound));
    }
}
