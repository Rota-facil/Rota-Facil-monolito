package com.rota.facil.client.http.controllers;


import com.rota.facil.client.http.dto.response.heatMaps.HeatpMapResponse;
import com.rota.facil.client.http.dto.request.heatMaps.RouteHeatMapRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface IntelligenceHttpClient {
    @PostExchange("/intelligence/route/heat-map")
    HeatpMapResponse generateHeatMapRoute(@RequestBody RouteHeatMapRequest request);
}
