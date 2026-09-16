CREATE TABLE IF NOT EXISTS institutions_tb (
    institution_id UUID PRIMARY KEY,
    prefecture_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_institutions_prefectures FOREIGN KEY (prefecture_id) REFERENCES prefectures_tb(prefecture_id)
);