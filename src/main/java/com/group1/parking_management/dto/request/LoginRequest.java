package com.group1.parking_management.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    String username;

    @Size(min = 8, message = "AUTH_PASSWORD_INVALID")
    String password;
}
