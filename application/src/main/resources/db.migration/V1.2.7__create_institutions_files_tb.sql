CREATE TABLE IF NOT EXISTS institutions_files_tb(
    file_id UUID PRIMARY KEY,
    institution_id UUID NOT NULL,

    CONSTRAINT fk_institutions_files_files FOREIGN KEY (file_id) REFERENCES files_tb(file_id),
    CONSTRAINT fk_institutions_files_institutions FOREIGN KEY (institution_id) REFERENCES institutions_tb(institution_id)
);