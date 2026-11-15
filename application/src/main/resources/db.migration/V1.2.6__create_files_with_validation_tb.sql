CREATE TABLE IF NOT EXISTS files_with_validation_tb (
    file_id UUID PRIMARY KEY,
    validation_status VARCHAR(20) NOT NULL DEFAULT 'ANALYSIS',

    CONSTRAINT files_with_validation_files FOREIGN KEY (file_id) REFERENCES files_tb(file_id)
);