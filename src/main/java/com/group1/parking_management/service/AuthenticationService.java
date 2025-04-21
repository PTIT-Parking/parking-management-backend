package com.group1.parking_management.service;

import org.springframework.stereotype.Service;

import com.group1.parking_management.dto.request.ChangePasswordRequest;
import com.group1.parking_management.dto.request.ForgotPasswordRequest;
import com.group1.parking_management.dto.request.LoginRequest;
import com.group1.parking_management.dto.request.LogoutRequest;
import com.group1.parking_management.dto.request.ResetPasswordRequest;
import com.group1.parking_management.dto.response.LoginResponse;
import com.group1.parking_management.dto.response.StaffResponse;

@Service
public interface AuthenticationService {
    public LoginResponse login(LoginRequest request);
    public void logout(LogoutRequest request);
    public StaffResponse getMyInfo();
    public void changePassword(ChangePasswordRequest request);
    public void forgotPassword(ForgotPasswordRequest request);
    public void resetPassword(ResetPasswordRequest request);
}
