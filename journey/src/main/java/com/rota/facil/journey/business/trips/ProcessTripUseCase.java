package com.rota.facil.journey.business.trips;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.journey.business.helpers.trips.FindTripByIdHelper;
import com.rota.facil.journey.business.helpers.trips.ProcessBoardPointArrivalHelper;
import com.rota.facil.journey.business.helpers.trips.ProcessInstitutionArrivalHelper;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.places.persistence.repositories.BoardPointRepository;
import com.rota.facil.places.persistence.repositories.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ProcessTripUseCase {
    private final ProcessBoardPointArrivalHelper processBoardPointArrivalHelper;
    private final ProcessInstitutionArrivalHelper processInstitutionArrivalHelper;
    private final FindTripByIdHelper findTripByIdHelper;
    private final TripRepository tripRepository;

    @Transactional
    public void execute(UUID tripId, Double latitude, Double longitude) {
        TripEntity tripFound = this.findTripByIdHelper.execute(tripId);

        tripFound.setLatitude(latitude);
        tripFound.setLongitude(longitude);

        Optional<InstitutionEntity> institutionFound = this.tripRepository.findInstitutionByIdAndCoordinates(tripId, latitude, longitude);
        Optional<BoardPointEntity> boardPointFound = this.tripRepository.findBoardPointByIdAndCoordinates(tripId, latitude, longitude);

        LocalDateTime now = LocalDateTime.now();
        institutionFound.ifPresent(institution -> this.processInstitutionArrivalHelper.execute(tripFound, institution, now));
        boardPointFound.ifPresent(boardPoint -> this.processBoardPointArrivalHelper.execute(tripFound, boardPoint, now));
    }
}
