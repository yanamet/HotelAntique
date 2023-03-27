package com.example.hotelantique.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Service
public class EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public void sendRegistrationEmail(String username, String userEmail) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {

            mimeMessageHelper.setFrom("hotel_antik_yana@abv.bg");
            mimeMessageHelper.setTo(userEmail);

            mimeMessageHelper.setSubject("Welcome to Hotel Antik !");
            mimeMessageHelper.setText(generateEmailText(username), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateEmailText(String username){

        Context context = new Context();
        context.setLocale(Locale.getDefault());
        context.setVariable("username", username);

        return templateEngine.process("email/register-email", context);
    }

}
