package com.rota.facil.journey.business.trips;

import com.rota.facil.journey.exceptions.MaxCapacityLimitedException;
import com.rota.facil.journey.exceptions.InvalidTripGoingReturnException;
import com.rota.facil.journey.exceptions.TripNotFoundException;
import com.rota.facil.journey.http.dto.request.trips.JoinInTripRequest;
import com.rota.facil.journey.http.dto.response.trips.TripUserResponse;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.entities.TripUserEntity;
import com.rota.facil.journey.persistence.mappers.TripUserMapper;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.journey.persistence.repositories.TripUserRepository;
import com.rota.facil.places.http.exceptions.BoardPointNotFoundException;
import com.rota.facil.places.http.exceptions.InstitutionNotFoundException;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.repositories.UserRepository;
import com.rota.facil.vehicles.entities.VehicleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JoinInTripUseCase {
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final TripUserRepository tripUserRepository;
    private final TripUserMapper tripUserMapper;

    public TripUserResponse execute(UUID tripId, UserEntity currentUser, JoinInTripRequest request) {
        if (!request.going() && !request.return_()) throw new InvalidTripGoingReturnException();
        TripEntity tripFound = this.tripRepository.findTripByIdAndPrefectureId(tripId, currentUser.getPrefectureId())
                .orElseThrow(TripNotFoundException::new);

        VehicleEntity vehicle = tripFound.getVehicle();

        long studentsToGoTrip = this.tripUserRepository.countStudentsToGoTrip(request.going(), request.return_()) + 1L;

        if (vehicle.getCapacity() >= studentsToGoTrip) throw new MaxCapacityLimitedException();


        BoardPointEntity boardPointFound  = this.tripRepository.findBoardPointByIdAndBoardPointId(tripId, request.boardPointId())
                        .orElseThrow(BoardPointNotFoundException::new);

        InstitutionEntity institutionFound = this.tripRepository.findInstitutionByIdAndInstitutionId(tripId, request.institutionId())
                        .orElseThrow(InstitutionNotFoundException::new);

        TripUserEntity userJoinInTrip = TripUserEntity.builder()
                .student(currentUser)
                .going(request.going())
                .return_(request.return_())
                .trip(tripFound)
                .institution(institutionFound)
                .boardPointEntity(boardPointFound)
                .build();


        tripFound.increaseStudents();
        currentUser.increaseTrips();

        this.userRepository.save(currentUser);
        this.tripRepository.save(tripFound);

        return this.tripUserMapper.map(this.tripUserRepository.save(userJoinInTrip));
    }
}
