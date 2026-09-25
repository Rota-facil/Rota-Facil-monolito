package com.rota.facil.journey.business.trips;

import com.rota.facil.journey.business.helpers.trips.FindTripByIdAndPrefectureHelper;
import com.rota.facil.journey.business.helpers.trips.RegisterIgnoredBoardPointsHelper;
import com.rota.facil.journey.business.helpers.trips.RegisterIgnoredInstitutionsHelper;
import com.rota.facil.journey.domain.Progress;
import com.rota.facil.journey.domain.TripOrientation;
import com.rota.facil.journey.exceptions.TripCannotBeStartedException;
import com.rota.facil.journey.http.dto.response.trips.TripResponse;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.mappers.TripMapper;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.repositories.UserRepository;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class InitTripUseCase {
    private final FindTripByIdAndPrefectureHelper findTripByIdAndPrefectureHelper;
    private final RegisterIgnoredBoardPointsHelper registerIgnoredBoardPointsHelper;
    private final RegisterIgnoredInstitutionsHelper registerIgnoredInstitutionsHelper;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final TripMapper tripMapper;

    @Transactional
    public TripResponse execute(UserEntity currentUser, UUID tripId) {
        TripEntity trip = findTripByIdAndPrefectureHelper.execute(tripId, currentUser.getPrefectureId());

        if (!Progress.NOT_STARTED.equals(trip.getActualStatus())) throw new TripCannotBeStartedException("A ida só pode ser iniciada quando a viagem ainda não foi iniciada");
        if (trip.getStudents().equals(0L)) throw new TripCannotBeStartedException("Não é possível iniciar a ida sem alunos cadastrados na viagem");

        trip.addNewStatus(Progress.STARTED);
        trip.getVehicle().setStatus(com.rota.facil.vehicles.domain.VehicleStatus.OPERATION);
        currentUser.moveToOnRoute();
        currentUser.increaseTrips();

        this.registerIgnoredBoardPointsHelper.execute(trip, TripOrientation.GOING);
        this.registerIgnoredInstitutionsHelper.execute(trip, TripOrientation.GOING);

        this.vehicleRepository.save(trip.getVehicle());
        this.userRepository.save(currentUser);
        return tripMapper.map(tripRepository.save(trip));
    }

}
