package com.example.springawscloudformationdemo.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.net.URL;

@Slf4j
@Service
public class AWSEmailService {

    @Autowired
    private AmazonSimpleEmailService amazonSimpleEmailService;

    static final String FROM = "xxx@xxx.com";

    static final String SUBJECT = "File arrived";

    public void sendEmail(URL url, String email) throws URISyntaxException {

        StringBuilder htmlBody = new StringBuilder();


        htmlBody.append("<h1>My java AWS file sharing app sent you this file</h1>");
        htmlBody.append( "<p>Your file is available at: <a href='"+url.toExternalForm()+"'>Link</a> </p>");


        try {
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(email))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(htmlBody.toString()))
                            )
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(SUBJECT)))
                    .withSource(FROM);
            amazonSimpleEmailService.sendEmail(request);
            log.info("Email sent");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }




}
