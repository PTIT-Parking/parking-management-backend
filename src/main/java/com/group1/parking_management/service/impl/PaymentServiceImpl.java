package com.group1.parking_management.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.group1.parking_management.dto.response.PaymentResponse;
import com.group1.parking_management.mapper.PaymentMapper;
import com.group1.parking_management.repository.PaymentRepository;
import com.group1.parking_management.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<PaymentResponse> getAllPayment() {
        return paymentRepository.findAllByOrderByCreateAtDesc().stream().map(paymentMapper::toResponse).toList();
    }

    @Override
    public List<PaymentResponse> getPaymentByDate(int month, int day) {
        int currentYear = LocalDateTime.now().getYear();

        LocalDate targetDate = LocalDate.of(currentYear, month, day);

        LocalDateTime startOfDay = targetDate.atStartOfDay();
        LocalDateTime endOfDay = targetDate.atTime(23, 59, 59);

        return paymentRepository.findByCreateAtBetweenOrderByCreateAtDesc(startOfDay, endOfDay).stream()
                .map(paymentMapper::toResponse).toList();
    }

}
