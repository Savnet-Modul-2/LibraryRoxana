package com.example.BookStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Verificare cont BookStore");
            message.setText("Codul tau de verificare este: " + code);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Eroare la trimiterea emailului: " + e.getMessage());
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

}