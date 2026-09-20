CREATE TABLE IF NOT EXISTS routes_recurring_tb (
    route_recurring_id UUID PRIMARY KEY,
    route_id UUID NOT NULL,
    vehicle_id UUID NOT NULL,


    CONSTRAINT fk_routes_recurring_routes FOREIGN KEY (route_id) REFERENCES routes_tb(route_id),
    CONSTRAINT fk_routes_recurring_vehicles FOREIGN KEY (vehicle_id) REFERENCES vehicles_tb(vehicle_id),
    CONSTRAINT u_route_vehicle UNIQUE (route_id, vehicle_id)
)