CREATE TABLE IF NOT EXISTS routes_institutions_tb (
    institution_route_id UUID PRIMARY KEY,
    route_id UUID NOT NULL,
    institution_id UUID NOT NULL,
    institution_time_going TIME NOT NULL,
    institution_time_finish TIME NOT NULL,

    CONSTRAINT fk_route_institutions_routes FOREIGN KEY (route_id) REFERENCES routes_tb(route_id),
    CONSTRAINT fk_route_institutions_institutions FOREIGN KEY (institution_id) REFERENCES institutions_tb(institution_id)
);