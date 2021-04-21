package com.example.springawscloudformationdemo.handler;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.example.springawscloudformationdemo.service.AWSEmailService;
import jdk.jfr.consumer.RecordedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.net.URL;

@Slf4j
@Component
public class S3EventHandler {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private AWSEmailService emailService;

    @SqsListener(value = "wortex-queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void listen(S3EventNotification message) throws Exception {
        log.debug(message.toJson());
        for (S3EventNotification.S3EventNotificationRecord record : message.getRecords()) {

            String bucket = record.getS3().getBucket().getName();
            String key = record.getS3().getObject().getKey();

            URL url = amazonS3.getUrl(bucket, key);

            emailService.sendEmail(url, "xxx@xxx.com");
        }

    }
}
