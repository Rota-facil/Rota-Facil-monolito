CREATE TABLE IF NOT EXISTS user_token_tb (
    user_token_id UUID NOT NULL PRIMARY KEY,
    user_id UUID NOT NULL,
    access_token TEXT NOT NULL,
    refresh_token TEXT NOT NULL,

    CONSTRAINT u_token_user_id UNIQUE (user_id),
    CONSTRAINT fk_user_token_users FOREIGN KEY (user_id) REFERENCES users_tb(user_id)
);