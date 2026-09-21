package com.rota.facil.files.storage;

import io.minio.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
public class MinioConfig {
    @Bean @Primary
    public MinioClient minioClient(@Value("${minio.url}") String url,
                                   @Value("${minio.access.key}") String accessKey,
                                   @Value("${minio.secret.key}") String secretKey,
                                   @Value("${minio.bucket.name}") String bucketName) throws Exception {
        MinioClient client = createClient(url, accessKey, secretKey);
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        return client;
    }

    @Bean @Qualifier("publicMinioClient")
    public MinioClient publicMinioClient(@Value("${minio.public.url}") String url,
                                         @Value("${minio.access.key}") String accessKey,
                                         @Value("${minio.secret.key}") String secretKey) {
        return createClient(url, accessKey, secretKey);
    }

    @Bean
    public MinioProperties minioProperties(@Value("${minio.bucket.name}") String bucketName) {
        return new MinioProperties(bucketName);
    }

    private MinioClient createClient(String url, String accessKey, String secretKey) {
        return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
    }
}
