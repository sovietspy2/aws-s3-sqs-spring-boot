package com.example.springawscloudformationdemo.handler;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.example.springawscloudformationdemo.config.SQSConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class S3EventHandler {

    @SqsListener(value = "wortex-queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void listen(S3EventNotification message) throws Exception {
        System.out.println(message);
        //throw new Exception("error");
        // processing
    }
}
