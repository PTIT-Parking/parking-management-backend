package com.group1.parking_management.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.group1.parking_management.common.ParkingType;
import com.group1.parking_management.dto.request.ParkingEntryRequest;
import com.group1.parking_management.dto.response.ParkingEntryResponse;
import com.group1.parking_management.entity.Account;
import com.group1.parking_management.entity.ParkingCard;
import com.group1.parking_management.entity.ParkingRecord;
import com.group1.parking_management.entity.VehicleType;
import com.group1.parking_management.exception.AppException;
import com.group1.parking_management.exception.ErrorCode;
import com.group1.parking_management.mapper.RecordMapper;
import com.group1.parking_management.repository.AccountRepository;
import com.group1.parking_management.repository.ActiveMonthlyRegistrationRepository;
import com.group1.parking_management.repository.ParkingCardRepository;
import com.group1.parking_management.repository.ParkingRecordRepository;
import com.group1.parking_management.repository.VehicleTypeRepository;
import com.group1.parking_management.service.ParkingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkingServiceImpl implements ParkingService {
    private final ParkingRecordRepository parkingRecordRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final ActiveMonthlyRegistrationRepository activeMonthlyRegistrationRepository;
    private final AccountRepository accountRepository;
    private final ParkingCardRepository parkingCardRepository;
    private final RecordMapper recordMapper;

    @Transactional
    @PreAuthorize("hasRole('STAFF')")
    public ParkingEntryResponse registerEntry(ParkingEntryRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account staff = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));

        if (!StringUtils.hasText(request.getLicensePlate()) && !StringUtils.hasText(request.getIdentifier())) {
            throw new AppException(ErrorCode.PARKING_IDENTIFICATION_ERROR);
        }
        if (StringUtils.hasText(request.getLicensePlate()) && parkingRecordRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new AppException(ErrorCode.PARKING_LICENSE_PLATE_EXISTED);
        }
        if (StringUtils.hasText(request.getIdentifier()) && parkingRecordRepository.existsByIdentifier(request.getIdentifier())) {
            throw new AppException(ErrorCode.PARKING_IDENTIFIER_EXISTED);
        }

        boolean hasMonthlyCard = activeMonthlyRegistrationRepository.existsByLicensePlate(request.getLicensePlate());
        ParkingType parkingType = hasMonthlyCard ? ParkingType.MONTHLY : ParkingType.DAILY;

        Integer cardId;
        try {
            cardId = Integer.parseInt(request.getCardId());
        } catch (NumberFormatException e) {
            throw new AppException(ErrorCode.PARKING_CARD_ID_INVALID);
        }
        
        if (parkingRecordRepository.existsByCard_CardId(cardId)) {
            throw new AppException(ErrorCode.PARKING_CARD_IN_USED);
        }
        
        ParkingCard parkingCard = parkingCardRepository.findById(cardId)
                .orElseThrow(() -> new AppException(ErrorCode.PARKING_CARD_NOT_FOUND));


        VehicleType vehicleType = vehicleTypeRepository.findById(request.getVehicleTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.PARKING_VEHICLE_TYPE_NOT_FOUND));

        ParkingRecord parkingRecord = ParkingRecord.builder()
                .licensePlate(request.getLicensePlate())
                .identifier(request.getIdentifier())
                .vehicleType(vehicleType)
                .card(parkingCard)
                .entryTime(LocalDateTime.now())
                .type(parkingType)
                .staffIn(staff)
                .build();

        return recordMapper.toParkingEntryResponse(parkingRecordRepository.save(parkingRecord));
    }
}
