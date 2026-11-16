package com.rota.facil.journey.business.useCases.metrics;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.journey.domain.Progress;
import com.rota.facil.journey.http.dto.response.metrics.MetricResponse;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.repositories.RouteRepository;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.users.domain.Role;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.repositories.UserRepository;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FetchMetricsUseCase {
    private final RouteRepository routeRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public MetricResponse execute(UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();
        List<TripEntity> tripsToday = tripRepository.findAllTodayByPrefectureId(prefectureId);
        long cancelledTrips = tripRepository.countByPrefectureIdAndActualStatus(prefectureId, Progress.CANCELLED);
        long tripsCancelledToday = tripsToday.stream().filter(this::isCancelled).count();
        long tripsFinishedToday = tripsToday.stream().filter(this::isFinished).count();
        long tripsWaiting = tripsToday.stream().filter(t -> t.getActualStatus() == Progress.NOT_STARTED).count();
        long tripsInRoute = tripsToday.stream().filter(this::isInRoute).count();
        long totalTripsStarted = tripsToday.stream().filter(this::isStarted).count();
        long studentsServed = tripsToday.stream().filter(this::isFinished)
                .mapToLong(t -> t.getStudents() == null ? 0 : t.getStudents()).sum();
        long pointTrips = tripsToday.stream().filter(t -> t.getStudents() != null && t.getStudents() > 0).count();
        double percentPointTrips = tripsToday.isEmpty() ? 0 : pointTrips * 100.0 / tripsToday.size();

        return new MetricResponse(
                routeRepository.countByPrefectureIdAndActiveTrue(prefectureId), tripsToday.size(), cancelledTrips,
                userRepository.countByPrefectureIdAndRoleAndActiveTrue(prefectureId, Role.STUDENT),
                userRepository.countByPrefectureIdAndRoleAndActiveTrue(prefectureId, Role.DRIVER),
                vehicleRepository.countByPrefectureIdAndActiveTrue(prefectureId), totalTripsStarted,
                percentPointTrips, studentsServed, tripsInRoute, tripsFinishedToday, tripsCancelledToday, tripsWaiting
        );
    }

    private boolean isCancelled(TripEntity trip) { return trip.getActualStatus() == Progress.CANCELLED; }
    private boolean isFinished(TripEntity trip) { return trip.getActualStatus() == Progress.RETURN_FINISHED; }
    private boolean isStarted(TripEntity trip) {
        return trip.getActualStatus() != Progress.NOT_STARTED && !isCancelled(trip);
    }
    private boolean isInRoute(TripEntity trip) {
        return switch (trip.getActualStatus()) {
            case STARTED, STARTED_FINISHED, RETURN_STARTED, INSTITUTION_ARRIVAL, BOARD_POINT_ARRIVAL -> true;
            default -> false;
        };
    }
}
