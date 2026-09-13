CREATE TABLE IF NOT EXISTS route_recurring_tb (
    route_recurring_id UUID PRIMARY KEY,
    route_id UUID NOT NULL,
    vehicle_id UUID NOT NULL,

    CONSTRAINT fk_route_recurring_route FOREIGN KEY (route_id) REFERENCES routes_tb(route_id) ON DELETE CASCADE,
    CONSTRAINT fk_route_recurring_vehicles FOREIGN KEY (vehicle_id) REFERENCES vehicles_tb(vehicle_id)
);