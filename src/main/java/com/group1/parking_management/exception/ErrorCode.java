package com.group1.parking_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    
    // Authentication & Authorization
    AUTH_INVALID_CREDENTIALS(1001, "Invalid username or password", HttpStatus.UNAUTHORIZED),
    AUTH_FORBIDDEN(1002, "Access denied", HttpStatus.FORBIDDEN),
    AUTH_UNAUTHENTICATED(1003, "Unauthenticated request", HttpStatus.UNAUTHORIZED),
    AUTH_UNAUTHORIZED(1004, "Unauthorized request", HttpStatus.FORBIDDEN),
    
    // Staff
    USERNAME_EXISTED(2001, "Username already exists", HttpStatus.BAD_REQUEST),
    STAFF_IDENTIFICATION_EXISTED(2002, "Identification already exists", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_FOUND(2003, "User not found", HttpStatus.NOT_FOUND),
    STAFF_NOT_FOUND(2004, "Staff not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(2005, "Role not found", HttpStatus.NOT_FOUND),

    //JWT (Token & Security)
    JWT_GENERATION_ERROR(3001, "Could not generate JWT token", HttpStatus.INTERNAL_SERVER_ERROR),
    JWT_INVALID(3002, "Invalid JWT token", HttpStatus.UNAUTHORIZED),
    JWT_EXPIRED(3003, "JWT token has expired", HttpStatus.UNAUTHORIZED),
    JWT_UNSUPPORTED(3004, "Unsupported JWT token", HttpStatus.BAD_REQUEST),

    // System error
    SYSTEM_INTERNAL_ERROR(9001, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM_UNKNOWN_ERROR(9999, "Unknown error", HttpStatus.INTERNAL_SERVER_ERROR);

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
