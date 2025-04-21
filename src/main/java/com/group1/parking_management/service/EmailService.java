package com.group1.parking_management.service;

public interface EmailService {
    void sendPasswordResetEmail(String toEmail, String username, String resetToken);
}
