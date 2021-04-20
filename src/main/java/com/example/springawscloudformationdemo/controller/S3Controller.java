package com.example.springawscloudformationdemo.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/files")
public class S3Controller {

    @Autowired
    private AmazonS3 amazonS3;

    @PostMapping
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        // validation required for filetype and name
        File tempFile = File.createTempFile("prefix-", "-suffix");
        file.transferTo(tempFile);
        amazonS3.putObject("wortex-my", file.getOriginalFilename(), tempFile);
        tempFile.deleteOnExit();
    }


    @GetMapping
    public List<String> listFiles() {
        return amazonS3.listObjects("wortex-my")
                .getObjectSummaries().stream()
                .map(item -> item.getKey())
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{key}")
    public ResponseEntity<InputStreamResource> getFile(@PathVariable String key) {

        GetObjectRequest getObjectRequest = new GetObjectRequest("wortex-my", key, false);
        S3Object object =  amazonS3.getObject(getObjectRequest);

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header("Content-Disposition", "attachment; filename=" + "myfile.txt")
                .body(new InputStreamResource(object.getObjectContent()));
    }

    @DeleteMapping(path = "/{key}")
    public void deleteResource(@PathVariable String key) {
        amazonS3.deleteObject(new DeleteObjectRequest("wortex-my", key));
    }


}
