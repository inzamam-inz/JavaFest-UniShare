package com.unishare.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSendingService {
    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sendMail(String toMail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("roughuse8633@gmail.com");
        message.setTo(toMail);
        message.setText(body);
        message.setSubject(subject);

        try {
            this.javaMailSender.send(message);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
