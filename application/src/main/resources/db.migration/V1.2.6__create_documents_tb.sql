CREATE TABLE IF NOT EXISTS documents_tb (
    file_id UUID PRIMARY KEY,
    is_valid BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_documents_files FOREIGN KEY (file_id) REFERENCES files_tb(file_id)
);