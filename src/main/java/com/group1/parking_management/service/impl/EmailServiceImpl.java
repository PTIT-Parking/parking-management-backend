package com.group1.parking_management.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.group1.parking_management.exception.AppException;
import com.group1.parking_management.exception.ErrorCode;
import com.group1.parking_management.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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

        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Đặt lại mật khẩu");

            String resetUrl = frontendUrl + "/reset-password?token=" + resetToken;

            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html lang=\"vi\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Đặt lại mật khẩu</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, Helvetica, sans-serif;\n" +
                    "            line-height: 1.6;\n" +
                    "            color: #333333;\n" +
                    "            max-width: 600px;\n" +
                    "            margin: 0 auto;\n" +
                    "            padding: 20px;\n" +
                    "        }\n" +
                    "        .header {\n" +
                    "            background-color: #4285f4;\n" +
                    "            padding: 20px;\n" +
                    "            text-align: center;\n" +
                    "            border-radius: 5px 5px 0 0;\n" +
                    "        }\n" +
                    "        .header h1 {\n" +
                    "            color: white;\n" +
                    "            margin: 0;\n" +
                    "            font-size: 24px;\n" +
                    "        }\n" +
                    "        .content {\n" +
                    "            background-color: #f9f9f9;\n" +
                    "            padding: 20px;\n" +
                    "            border: 1px solid #dddddd;\n" +
                    "            border-top: none;\n" +
                    "            border-radius: 0 0 5px 5px;\n" +
                    "        }\n" +
                    "        .button {\n" +
                    "            display: inline-block;\n" +
                    "            background-color: #4285f4;\n" +
                    "            color: white !important;\n" +
                    "            text-decoration: none;\n" +
                    "            padding: 12px 24px;\n" +
                    "            margin: 20px 0;\n" +
                    "            border-radius: 4px;\n" +
                    "            font-weight: bold;\n" +
                    "        }\n" +
                    "        .footer {\n" +
                    "            margin-top: 20px;\n" +
                    "            text-align: center;\n" +
                    "            font-size: 12px;\n" +
                    "            color: #777777;\n" +
                    "        }\n" +
                    "        .info {\n" +
                    "            background-color: #fff8e1;\n" +
                    "            border-left: 4px solid #ffca28;\n" +
                    "            padding: 12px;\n" +
                    "            margin: 15px 0;\n" +
                    "            font-size: 14px;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"header\">\n" +
                    "        <h1>Đặt Lại Mật Khẩu</h1>\n" +
                    "    </div>\n" +
                    "    <div class=\"content\">\n" +
                    "        <p>Xin chào <strong>" + username + "</strong>,</p>\n" +
                    "        <p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn trên hệ thống Parking Management.</p>\n"
                    +
                    "        <p>Vui lòng nhấn vào nút dưới đây để đặt lại mật khẩu:</p>\n" +
                    "        \n" +
                    "        <div style=\"text-align: center;\">\n" +
                    "            <a href=\"" + resetUrl + "\" class=\"button\">Đặt Lại Mật Khẩu</a>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <div class=\"info\">\n" +
                    "            <p>⚠️ <strong>Lưu ý quan trọng:</strong> Đường dẫn này sẽ hết hạn sau 15 phút.</p>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <p>Nếu nút không hoạt động, bạn có thể sao chép và dán đường dẫn sau vào trình duyệt:</p>\n"
                    +
                    "        <p style=\"word-break: break-all;\">" + resetUrl + "</p>\n" +
                    "        \n" +
                    "        <p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này hoặc liên hệ với quản trị viên hệ thống.</p>\n"
                    +
                    "    </div>\n" +
                    "    <div class=\"footer\">\n" +
                    "        <p>Email này được gửi tự động, vui lòng không trả lời.</p>\n" +
                    "        <p>© 2025 Parking Management System. All rights reserved.</p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";

            helper.setText(htmlContent, true); // true indicates that this is HTML content

            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.AUTH_EMAIL_RESET_FAIL_TO_SEND);
        }

    }

}
