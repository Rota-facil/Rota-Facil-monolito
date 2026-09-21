CREATE TABLE IF NOT EXISTS files_tb (
    file_id UUID PRIMARY KEY,
    original_filename VARCHAR(255) NOT NULL,
    object_key VARCHAR(500) NOT NULL UNIQUE,
    owner_id UUID NOT NULL,
    creator_id UUID NOT NULL,
    prefecture_id UUID NOT NULL,
    owner_type VARCHAR(30) NOT NULL,
    file_category VARCHAR(30) NOT NULL,
    mime_type VARCHAR(100) NOT NULL,
    file_size_bytes BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT files_creator_fk FOREIGN KEY (creator_id) REFERENCES users_tb(user_id),
    CONSTRAINT files_prefecture_fk FOREIGN KEY (prefecture_id) REFERENCES prefectures_tb(prefecture_id)
);
CREATE INDEX files_owner_category_idx ON files_tb (owner_id, prefecture_id, file_category);
