package org.example.test.dto;

import org.springframework.context.annotation.Configuration;

@Configuration
public interface MailService {
    void sendMail (EmailRequest request);
}
