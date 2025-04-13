package com.group1.parking_management.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group1.parking_management.entity.ActiveMonthlyRegistration;
import com.group1.parking_management.entity.ExpireMonthlyRegistration;
import com.group1.parking_management.repository.ActiveMonthlyRegistrationRepository;
import com.group1.parking_management.repository.ExpireMonthlyRegistrationRepository;
import com.group1.parking_management.service.RegistrationExpirationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationExpirationServiceImpl implements RegistrationExpirationService {
    private final ActiveMonthlyRegistrationRepository activeMonthlyRegistrationRepository;
    private final ExpireMonthlyRegistrationRepository expireMonthlyRegistrationRepository;

    @Override
    @Transactional
    public int processExpiredRegistrations() {
        LocalDateTime now = LocalDateTime.now();

        // Find all expired registration (expirationDate < now)
        List<ActiveMonthlyRegistration> expiredRegistrations = activeMonthlyRegistrationRepository
                .findByExpirationDateBefore(now);

        if (expiredRegistrations.isEmpty()) {
            log.info("No expired monthly registrations found");
            return 0;
        }

        log.info("Found {} expired monthly registrations to process", expiredRegistrations.size());

        // Move data to expire monthly registration
        for (ActiveMonthlyRegistration active : expiredRegistrations) {
            ExpireMonthlyRegistration expire = ExpireMonthlyRegistration.builder()
                    .issueDate(active.getIssueDate())
                    .expirationDate(active.getExpirationDate())
                    .vehicle(active.getVehicle())
                    .customer(active.getCustomer())
                    .createBy(active.getCreateBy())
                    .payment(active.getPayment())
                    .build();

            expireMonthlyRegistrationRepository.save(expire);
            activeMonthlyRegistrationRepository.delete(active);
        }

        log.info("Successfully processed {} expired monthly registrations", expiredRegistrations.size());
        return expiredRegistrations.size();
    }
}
