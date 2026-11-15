CREATE TABLE IF NOT EXISTS vehicles_files_tb (
    file_id UUID PRIMARY KEY,
    vehicle_id UUID NOT NULL,

    CONSTRAINT fk_vehicles_files_files FOREIGN KEY (file_id) REFERENCES files_tb(file_id),
    CONSTRAINT fk_vehicles_files_vehicles FOREIGN KEY (vehicle_id) REFERENCES vehicles_tb(vehicle_id)
);