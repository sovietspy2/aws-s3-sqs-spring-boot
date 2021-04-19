package com.example.springawscloudformationdemo.controller;

import com.example.springawscloudformationdemo.config.S3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class S3Controller {

    @Autowired
    private S3Config s3Config;

    @GetMapping("/upload")
    public void uploadFile() throws IOException {
        /// file should be coming from REST API but this is for S3 test so im gonna just use smt

        File file = new File("D:\\work\\spring-aws-cloudformation-demo\\src\\main\\resources\\example.txt");
        s3Config.getAmazonS3().putObject("wortex-my", UUID.randomUUID().toString(), file);
    }

    @GetMapping("/files")
    public List<String> listFiles() {

        return s3Config.getAmazonS3().listObjects("wortex-my")
                .getObjectSummaries().stream()
                .map(item -> item.getKey())
                .collect(Collectors.toList());
    }

}
