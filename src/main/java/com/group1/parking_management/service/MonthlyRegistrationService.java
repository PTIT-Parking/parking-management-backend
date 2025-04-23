package com.group1.parking_management.service;

import java.util.List;

import com.group1.parking_management.dto.request.MonthlyRegistrationRequest;
import com.group1.parking_management.dto.response.MonthlyRegistrationResponse;

public interface MonthlyRegistrationService {
    public MonthlyRegistrationResponse createMonthlyRegistration(MonthlyRegistrationRequest request);

    public List<MonthlyRegistrationResponse> getAllActiveRegistration();

    public List<MonthlyRegistrationResponse> getAllExpireRegistration();
}
