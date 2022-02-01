package com.mert.minio.controller;

import com.mert.minio.adapter.MinioAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/storage")
public class StorageController {
    @Autowired
    MinioAdapter minioAdapter;

    @GetMapping(path = "/object/{objectName}")
    public ResponseEntity<Resource> getBucketsImages(@PathVariable String objectName) {
        var data = minioAdapter.getFile(objectName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(data.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+objectName+"\"")
                .body(new ByteArrayResource(data));
    }

    @PostMapping(path = "/upload-image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Resource> savePostPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        var image = minioAdapter.uploadFile(file);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getName() + "\"")
                .body(new ByteArrayResource(image.getBytes()));
    }

}
