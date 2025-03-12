package com.group1.parking_management.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
    private String secretKey;

    @Value("${spring.security.oauth2.resourceserver.jwt.valid-duration}")
    private String expirationTime; 

    // public String generateToken(String username) {

    // }
}
