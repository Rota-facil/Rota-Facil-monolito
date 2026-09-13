CREATE TABLE IF NOT EXISTS users_tb (
    user_id UUID PRIMARY KEY,
    prefecture_id UUID NOT NULL,
    name VARCHAR(120) NOT NULL,
    email VARCHAR (120) NOT NULL,
    cpf VARCHAR(14),
    password VARCHAR(120),
    google_id TEXT,
    trips BIGINT NOT NULL DEFAULT 0,
    completed_trips BIGINT NOT NULL DEFAULT 0,
    score DOUBLE PRECISION NOT NULL DEFAULT 5,
    role VARCHAR(10) NOT NULL,
    status VARCHAR(20),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,


    CONSTRAINT fk_users_prefectures FOREIGN KEY (prefecture_id) REFERENCES prefectures_tb(prefecture_id),
    CONSTRAINT u_email UNIQUE(email),
    CONSTRAINT u_cpf UNIQUE(cpf)
);