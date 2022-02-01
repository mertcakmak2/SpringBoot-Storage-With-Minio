package com.mert.minio.adapter;

import com.mert.minio.listener.BucketListener;
import io.minio.BucketEventListener;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import io.minio.messages.EventType;
import io.minio.messages.NotificationConfiguration;
import io.minio.messages.QueueConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MinioAdapter {

    @Autowired
    MinioClient minioClient;

    @Autowired
    BucketListener bucketListener;

    @Value("${minio.buckek.name}")
    String defaultBucketName;

    public List<Bucket> getAllBuckets() throws Exception {
            return minioClient.listBuckets();
    }

    public String createBucket(String bucketName) {
        try {
            minioClient.makeBucket(bucketName);
            return bucketName+ "bucket created";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public byte[] getFile(String objectName) {
        try {
            InputStream file = minioClient.getObject(defaultBucketName,objectName);
            return file.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public MultipartFile uploadFile(MultipartFile file) throws IOException {
        String id = UUID.randomUUID().toString() + file.getOriginalFilename();
        try {
            minioClient.putObject(defaultBucketName,id,file.getInputStream(),"image/jpeg");
        } catch (MinioException e) {
            throw new IllegalStateException("The file cannot be upload on the internal storage. Please retry later", e);
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | XmlPullParserException e) {
            throw new IllegalStateException("The file cannot be read", e);
        }
        return file;
    }

}