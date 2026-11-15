package com.rota.facil.files.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Table(name = "documents_tb")
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "file_id")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEntity extends FileEntity {
    @Builder.Default
    private boolean isValid = false;
}
