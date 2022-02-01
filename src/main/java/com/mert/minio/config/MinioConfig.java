package com.mert.minio.config;

import com.mert.minio.listener.BucketListener;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

@Configuration
public class MinioConfig {
    @Value("${minio.access.name}")
    String accessKey;
    @Value("${minio.access.secret}")
    String accessSecret;
    @Value("${minio.url}")
    String minioUrl;

    @Bean
    public MinioClient generateMinioClient() {
        try {
            MinioClient client = new MinioClient(minioUrl,accessKey,accessSecret);
            CompletableFuture.runAsync(()->{
                try {
                    client.listenBucketNotification("testbucket", "", "",
                            new String[]{"s3:ObjectCreated:*","s3:ObjectRemoved:*"}, new BucketListener());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            return client;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }



}