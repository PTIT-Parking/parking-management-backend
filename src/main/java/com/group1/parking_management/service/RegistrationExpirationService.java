package com.group1.parking_management.service;

import org.springframework.stereotype.Service;

@Service
public interface RegistrationExpirationService {
    public int processExpiredRegistrations();
}
