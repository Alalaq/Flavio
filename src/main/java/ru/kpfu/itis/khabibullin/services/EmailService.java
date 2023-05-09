package ru.kpfu.itis.khabibullin.services;

import jakarta.mail.MessagingException;

/**
 * @author Khabibullin Alisher
 */
public interface EmailService {
    void sendVerificationEmail(String to, String verificationLink) throws MessagingException;
}
