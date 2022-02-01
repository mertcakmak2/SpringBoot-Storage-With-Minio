package com.mert.minio.listener;

import io.minio.BucketEventListener;
import io.minio.notification.NotificationInfo;
import org.springframework.stereotype.Service;

@Service
public class BucketListener implements BucketEventListener {

    @Override
    public void updateEvent(NotificationInfo notificationInfo) {
        System.out.println("Bucket event listener: "+notificationInfo);
        System.out.println(notificationInfo.records[0].eventName);
        System.out.println(notificationInfo.records[0].s3.object.key);
        System.out.println(notificationInfo.records[0].s3.bucket.name);
    }
}
