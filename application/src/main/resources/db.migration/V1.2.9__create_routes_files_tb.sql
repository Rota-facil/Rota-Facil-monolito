CREATE TABLE IF NOT EXISTS routes_files_tb (
    file_id UUID PRIMARY KEY,
    route_id UUID NOT NULL,

    CONSTRAINT fk_routes_files_files FOREIGN KEY (file_id) REFERENCES files_tb(file_id),
    CONSTRAINT fk_routes_files_routes FOREIGN KEY (route_id) REFERENCES routes_tb(route_id)
);