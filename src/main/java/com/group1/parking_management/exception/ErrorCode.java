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
    AUTH_WRONG_PASSWORD(1005, "Password is incorrect", HttpStatus.BAD_REQUEST),
    AUTH_PASSWORD_SAME_AS_OLD(1006, "New password must be different from the old password", HttpStatus.BAD_REQUEST),
    
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

    // Parking Exception
    PARKING_IDENTIFICATION_ERROR(4001, "Vehicle must have license plate or identifier", HttpStatus.BAD_REQUEST),
    PARKING_VEHICLE_TYPE_NOT_FOUND(4002, "Vehicle type not found", HttpStatus.NOT_FOUND),
    PARKING_CARD_ID_INVALID(4003, "Card id invalid", HttpStatus.BAD_REQUEST),
    PARKING_CARD_NOT_FOUND(4004, "Parking card not found", HttpStatus.NOT_FOUND),
    PARKING_CARD_IN_USED(4005, "Parking card being used", HttpStatus.BAD_REQUEST),
    PARKING_LICENSE_PLATE_EXISTED(4006, "Lisence plate existed", HttpStatus.BAD_REQUEST),
    PARKING_IDENTIFIER_EXISTED(4007, "License plate existed", HttpStatus.BAD_REQUEST),

    // System error
    SYSTEM_INTERNAL_ERROR(9001, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM_UNKNOWN_ERROR(9999, "Unknown error", HttpStatus.INTERNAL_SERVER_ERROR);

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
