package com.group1.parking_management.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.group1.parking_management.common.Role;
import com.group1.parking_management.entity.Account;
import com.group1.parking_management.entity.ParkingCard;
import com.group1.parking_management.entity.Price;
import com.group1.parking_management.entity.VehicleType;
import com.group1.parking_management.repository.AccountRepository;
import com.group1.parking_management.repository.ParkingCardRepository;
import com.group1.parking_management.repository.PriceRepository;
import com.group1.parking_management.repository.VehicleTypeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final PriceRepository priceRepository;
    private final ParkingCardRepository parkingCardRepository; 

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(AccountRepository accountRepository) {
        log.info("Init Applicaion...");
        return arg -> {
            if (accountRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {
                Account account = Account.builder()
                        .username(ADMIN_USERNAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .role(Role.ADMIN)
                        .build();
                accountRepository.save(account);
                log.warn("admin user has been created with default password: admin, please change it");
            }

            if (parkingCardRepository.count() == 0) {
                List<ParkingCard> listCard = new ArrayList<>();
                for (int i = 0; i <= 1000; i++) {
                    listCard.add(new ParkingCard(i));
                }
                parkingCardRepository.saveAll(listCard);
                log.info("Parking Cards initialized");
            }
            
            if (vehicleTypeRepository.count() == 0) {
                String bikeId = UUID.randomUUID().toString();
                String motorId = UUID.randomUUID().toString();
                String scooterId = UUID.randomUUID().toString();
                VehicleType bike = new VehicleType(bikeId, "Bicycle");
                VehicleType motorbike =  new VehicleType(motorId, "Motorbike");
                VehicleType scooter =  new VehicleType(scooterId, "Scooter");

                vehicleTypeRepository.saveAll(List.of(bike, motorbike, scooter));
                
                priceRepository.saveAll(List.of(
                    new Price(bikeId, bike, 1000, 2000, 25000),
                    new Price(motorId, motorbike, 3000, 4000, 120000),
                    new Price(scooterId, scooter, 4000, 5000, 150000)
                ));

                log.info("Vehicle Type ");
            }

            log.info("Application initialization completed.....");
        };   
    }
}
