package com.group1.parking_management.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.group1.parking_management.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendPasswordResetEmail(String toEmail, String username, String resetToken) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Đặt lại mật khẩu");

        String resetUrl = frontendUrl + "/reset-password?token=" + resetToken;

        message.setText("Xin chào " + username + ",\n\n" +
                "Bạn đã yêu cầu đặt lại mật khẩu. Vui lòng click vào đường dẫn dưới đây để đặt lại mật khẩu:\n\n" +
                resetUrl + "\n\n" +
                "Đường dẫn này sẽ hết hạn sau 15 phút.\n\n" +
                "Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.");
        
        emailSender.send(message);

    }

}
