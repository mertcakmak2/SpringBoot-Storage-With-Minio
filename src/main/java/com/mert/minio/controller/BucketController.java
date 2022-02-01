package com.mert.minio.controller;

import com.mert.minio.adapter.MinioAdapter;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buckets")
public class BucketController {

    @Autowired
    MinioAdapter minioAdapter;


    @GetMapping(path = "")
    public List<Bucket> getAllBuckets() throws Exception {
        return minioAdapter.getAllBuckets();
    }

    @GetMapping(path = "/{bucketName}")
    public String createBucket(@PathVariable String bucketName){
        return minioAdapter.createBucket(bucketName);
    }
}
