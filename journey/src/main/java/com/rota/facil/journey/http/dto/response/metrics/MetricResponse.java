package com.rota.facil.journey.http.dto.response.metrics;

public record MetricResponse(
        long activeRoutes, long tripsToday, long cancelledTrips, long students,
        long drivers, long bus, long totalTripsStarted, double percentPointTrips,
        long studentsServed, long tripsInRoute, long tripsFinishedToday,
        long tripsCancelledToday, long tripsWaiting
) {}
