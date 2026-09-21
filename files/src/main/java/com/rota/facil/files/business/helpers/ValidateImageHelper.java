package com.rota.facil.files.business.helpers;
import com.rota.facil.files.exceptions.InvalidFileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class ValidateImageHelper {
    public void execute(MultipartFile file) {
        if (file == null || file.isEmpty()) throw new InvalidFileException("A foto não pode estar vazia");
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new InvalidFileException("O arquivo deve ser uma imagem");
        }
    }
}
