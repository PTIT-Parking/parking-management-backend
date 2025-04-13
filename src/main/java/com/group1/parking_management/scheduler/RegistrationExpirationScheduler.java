package com.group1.parking_management.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.group1.parking_management.service.RegistrationExpirationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationExpirationScheduler {
    private final RegistrationExpirationService registrationExpirationService;

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Ho_Chi_Minh")  // Chạy lúc 0h00 hàng ngày
    public void checkAndProcessExpiredRegistrations() {
        log.info("Starting scheduled task: Processing expired monthly registrations");
        int count = registrationExpirationService.processExpiredRegistrations();
        log.info("Completed scheduled task: Processed {} expired registrations", count);
    }

    @Scheduled(initialDelay = 60000, fixedDelay = Long.MAX_VALUE)  // Chạy 1 phút sau khi ứng dụng khởi động
    public void processExpiredRegistrationsOnStartup() {
        log.info("Running startup task: Processing expired monthly registrations");
        int count = registrationExpirationService.processExpiredRegistrations();
        log.info("Completed startup task: Processed {} expired registrations", count);
    }
}
