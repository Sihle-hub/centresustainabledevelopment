package com.example.demo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService {
	
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,String subject,String body ) {
    	
    	
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("csd@application.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Send...");


         }
    
     public void sendEmailWithAttachments(String to, String subject, String text, MultipartFile... attachments) throws MessagingException {
        // Create a new MimeMessage
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // Create a MimeMessageHelper with the ability to handle multiple attachments
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        // Set the recipient, subject, and body text of the email
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(text);

        // Add each attachment to the email
        for (MultipartFile attachment : attachments) {
            if (!attachment.isEmpty()) {
                try {
                    // Convert MultipartFile to a ByteArrayResource
                    Resource resource = new ByteArrayResource(attachment.getBytes());
                    messageHelper.addAttachment(attachment.getOriginalFilename(), resource);
                } catch (IOException e) {
                    throw new MessagingException("Failed to attach file: " + attachment.getOriginalFilename(), e);
                }
            }
        }

        // Send the email
        mailSender.send(mimeMessage);
    }

    }
