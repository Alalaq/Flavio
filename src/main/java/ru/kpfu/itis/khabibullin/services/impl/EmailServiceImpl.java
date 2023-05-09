package ru.kpfu.itis.khabibullin.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.khabibullin.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to,
                                      String verificationLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Verify your email");
        helper.setText("<p>Please click the following link to verify your email:</p>"
                + "<p><a href='" + verificationLink + "'>" + verificationLink + "</a></p>", true);

        mailSender.send(message);
    }
}

