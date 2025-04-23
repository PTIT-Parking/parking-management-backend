package com.group1.parking_management.service;

import java.util.List;

import com.group1.parking_management.dto.response.PaymentResponse;

public interface PaymentService {
    public List<PaymentResponse> getAllPayment(); 
    public List<PaymentResponse> getPaymentByDate(int month, int year);
}
