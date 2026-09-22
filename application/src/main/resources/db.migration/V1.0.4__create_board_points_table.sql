CREATE TABLE IF NOT EXISTS board_points_tb (
    board_point_id UUID PRIMARY KEY,
    prefecture_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    geom geography NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_board_points_prefectures FOREIGN KEY (prefecture_id) REFERENCES prefectures_tb(prefecture_id)
);