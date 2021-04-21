package com.example.springawscloudformationdemo.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SESConfig {

    @Value("${key}")
    private String awsAccessKey;

    @Value("${secret}")
    private String awsSecretKey;

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService() {

        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsAccessKey, awsSecretKey)
        );
        return AmazonSimpleEmailServiceClientBuilder.standard()
                        .withCredentials(awsCredentialsProvider)
                        .withRegion(Regions.EU_CENTRAL_1).build();
    }


}
