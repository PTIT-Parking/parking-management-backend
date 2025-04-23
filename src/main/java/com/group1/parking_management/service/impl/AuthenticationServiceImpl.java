package com.group1.parking_management.service.impl;

import java.text.ParseException;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group1.parking_management.common.Role;
import com.group1.parking_management.dto.request.ChangePasswordRequest;
import com.group1.parking_management.dto.request.ForgotPasswordRequest;
import com.group1.parking_management.dto.request.LoginRequest;
import com.group1.parking_management.dto.request.LogoutRequest;
import com.group1.parking_management.dto.request.ResetPasswordRequest;
import com.group1.parking_management.dto.response.LoginResponse;
import com.group1.parking_management.dto.response.StaffResponse;
import com.group1.parking_management.entity.Account;
import com.group1.parking_management.entity.Staff;
import com.group1.parking_management.exception.AppException;
import com.group1.parking_management.exception.ErrorCode;
import com.group1.parking_management.repository.AccountRepository;
import com.group1.parking_management.repository.StaffRepository;
import com.group1.parking_management.service.AuthenticationService;
import com.group1.parking_management.service.EmailService;
import com.group1.parking_management.service.RedisService;
import com.group1.parking_management.util.JwtUtil;
import com.nimbusds.jose.JOSEException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AccountRepository accountRepository;
    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final EmailService emailService;

    public LoginResponse login(LoginRequest request) {
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.AUTH_INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new AppException(ErrorCode.AUTH_INVALID_CREDENTIALS);
        }

        if (account.getRole() == Role.STAFF) {
            Staff staff = staffRepository.findById(account.getAccountId())
                    .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));
            if (Boolean.FALSE.equals(staff.getIsActive())) {
                throw new AppException(ErrorCode.STAFF_STATUS_DISABLED);
            }

        }

        String token = jwtUtil.generateToken(account.getUsername(), account.getRole().toString());
        return LoginResponse.builder()
                .token(token)
                .username(account.getUsername())
                .role(account.getRole().toString())
                .build();
    }

    public void logout(LogoutRequest request) {
        String token = request.getToken();
        if (token == null || token.isEmpty()) {
            throw new AppException(ErrorCode.JWT_INVALID);
        }

        try {
            jwtUtil.validateToken(token);
            long expiration = jwtUtil.getExpirationTime(token) / 1000;
            long now = System.currentTimeMillis() / 1000;
            long ttl = expiration - now;

            if (ttl > 0) {
                redisService.addToBlacklist(token, ttl);
            }
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.JWT_INVALID);
        }
    }

    @PreAuthorize("hasRole('STAFF')")
    public StaffResponse getMyInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));
        Staff staff = staffRepository.findById(account.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));
        return StaffResponse.builder()
                .username(username)
                .identification(staff.getIdentification())
                .name(staff.getName())
                .dob(staff.getDob())
                .gender(staff.getGender())
                .phoneNumber(staff.getPhoneNumber())
                .address(staff.getAddress())
                .email(staff.getEmail())
                .build();
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));

        if (!passwordEncoder.matches(request.getOldPassword(), account.getPassword())) {
            throw new AppException(ErrorCode.AUTH_WRONG_PASSWORD);
        }

        if (passwordEncoder.matches(request.getNewPassword(), account.getPassword())) {
            throw new AppException(ErrorCode.AUTH_PASSWORD_SAME_AS_OLD);
        }

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        accountRepository.save(account);
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));

        Staff staff = staffRepository.findById(account.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));

        if (!staff.getEmail().equals(request.getEmail())) {
            throw new AppException(ErrorCode.AUTH_EMAIL_MISMATCH);
        }

        String resetToken = UUID.randomUUID().toString();
        redisService.addResetToken(resetToken, account.getUsername());

        emailService.sendPasswordResetEmail(staff.getEmail(), account.getUsername(), resetToken);

    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {

        String redisKey = "reset_token:" + request.getToken();
        String username = redisService.getValue(redisKey);

        if (username == null) {
            throw new AppException(ErrorCode.AUTH_RESET_TOKEN_INVALID);
        }

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));

        if (passwordEncoder.matches(request.getNewPassword(), account.getPassword())) {
            throw new AppException(ErrorCode.AUTH_PASSWORD_SAME_AS_OLD);
        }

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        accountRepository.save(account);

        redisService.deleteValue(redisKey);
    }

    public boolean validateResetToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        String redisKey = "reset_token:" + token;
        String username = redisService.getValue(redisKey);

        return username != null;
    }
}
