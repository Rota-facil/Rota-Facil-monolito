package com.rota.facil.files.storage;
import org.springframework.web.multipart.MultipartFile;
public interface FileStorage {
    void upload(MultipartFile file, String objectKey);
    String createTemporaryUrl(String objectKey);
}
