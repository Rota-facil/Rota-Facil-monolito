CREATE TABLE IF NOT EXISTS bus_tb (
    bus_id UUID PRIMARY KEY,
    driver_id UUID,
    prefecture_id UUID NOT NULL,
    capacity BIGINT NOT NULL,
    plate VARCHAR(10) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    status VARCHAR(30) NOT NULL DEFAULT 'OUT_OF_OPERATION',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT bus_users FOREIGN KEY (driver_id) REFERENCES users_tb(user_id)
);