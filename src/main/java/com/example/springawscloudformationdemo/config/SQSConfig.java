package com.example.springawscloudformationdemo.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;

@EnableSqs
@Configuration
public class SQSConfig {

    @Value("${sqs.endpoint}")
    private String sqsUrl;

    @Value("${key}")
    private String awsAccessKey;

    @Value("${secret}")
    private String awsSecretKey;

    private AmazonSQSAsync client;

    @PostConstruct
    private void init() {
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsAccessKey, awsSecretKey)
        );
        client = AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsUrl, Regions.EU_CENTRAL_1.getName()))
                .build();
    }

    @Bean
    public QueueMessagingTemplate getMessageTemplate(){
        return new QueueMessagingTemplate(client);
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync(){
        return client;
    }

    public String getSqsUrl() {
        return sqsUrl;
    }
}
