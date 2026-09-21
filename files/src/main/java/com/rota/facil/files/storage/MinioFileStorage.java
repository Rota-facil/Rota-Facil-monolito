package com.rota.facil.files.storage;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.util.concurrent.TimeUnit;

@Component
public class MinioFileStorage implements FileStorage {
    private final MinioClient minioClient;
    private final MinioClient publicMinioClient;
    private final MinioProperties properties;

    public MinioFileStorage(MinioClient minioClient,
                            @Qualifier("publicMinioClient") MinioClient publicMinioClient,
                            MinioProperties properties) {
        this.minioClient = minioClient;
        this.publicMinioClient = publicMinioClient;
        this.properties = properties;
    }

    public void upload(MultipartFile file, String objectKey) {
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(properties.bucketName()).object(objectKey)
                    .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build());
        } catch (Exception exception) {
            throw new IllegalStateException("Não foi possível armazenar o arquivo", exception);
        }
    }

    public String createTemporaryUrl(String objectKey) {
        try {
            return publicMinioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(properties.bucketName()).object(objectKey).method(Method.GET)
                    .expiry(1, TimeUnit.DAYS).build());
        } catch (Exception exception) {
            throw new IllegalStateException("Não foi possível gerar a URL do arquivo", exception);
        }
    }
}
