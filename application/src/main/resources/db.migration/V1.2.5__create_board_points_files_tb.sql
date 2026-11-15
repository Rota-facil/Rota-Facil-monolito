CREATE TABLE IF NOT EXISTS board_points_files_tb(
    file_id UUID PRIMARY KEY,
    board_point_id UUID NOT NULL,

    CONSTRAINT fk_board_points_files_files FOREIGN KEY (file_id) REFERENCES files_tb(file_id),
    CONSTRAINT fk_board_points_files_board_points FOREIGN KEY (board_point_id) REFERENCES board_points_tb(board_point_id)
);