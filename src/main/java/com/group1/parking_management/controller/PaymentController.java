package com.group1.parking_management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group1.parking_management.dto.ApiResponse;
import com.group1.parking_management.dto.response.PaymentResponse;
import com.group1.parking_management.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public ApiResponse<List<PaymentResponse>> getAllPayment() {
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getAllPayment())
                .build();
    }
}
