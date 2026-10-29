package com.springmaile.services.impl;

import com.springmaile.services.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class EmailImpl implements Email {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${app.base-url}")
    private String baseUrl;

    public EmailImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(String to, String name) {
        try (InputStream is = new ClassPathResource("templates/welcome-email.html").getInputStream()) {

            String link = baseUrl + "/welcome.html?name=" + URLEncoder.encode(name, "UTF-8");

            String html = StreamUtils.copyToString(is, StandardCharsets.UTF_8)
                    .replace("{{name}}", name)
                    .replace("{{link}}", link);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("🎉 Welcome, " + name + "!");
            helper.setText(html, true); // true = HTML
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send mail", e);
        }
    }
}