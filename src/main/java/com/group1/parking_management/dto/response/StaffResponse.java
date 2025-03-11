package com.group1.parking_management.dto.response;

import java.time.LocalDate;

import com.group1.parking_management.constant.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffResponse {
    private String accountId;
    private String username;
    private String identification;
    private String name;
    private LocalDate dob;
    private Gender gender;
    private String phoneNumber;
    private String address;
    private String email;
    private Boolean isActive;
}
