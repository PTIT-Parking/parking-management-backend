package com.group1.parking_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED(1002, "Username existed", HttpStatus.BAD_REQUEST),
    IDENTIFICATION_EXISTED(1003, "Identification existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1004, "User not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1005, "Role not found", HttpStatus.NOT_FOUND),
    STAFF_NOT_FOUND(1006, "Staff not found", HttpStatus.NOT_FOUND),
    UNCATEGORIZE_EXCEPTION(9999, "Uncategorize error", HttpStatus.INTERNAL_SERVER_ERROR);

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
