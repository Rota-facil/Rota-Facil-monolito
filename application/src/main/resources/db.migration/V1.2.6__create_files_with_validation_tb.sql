CREATE TABLE IF NOT EXISTS files_with_validation_tb (
    file_id UUID PRIMARY KEY,
    is_valid BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT files_with_validation_files FOREIGN KEY (file_id) REFERENCES files_tb(file_id)
);