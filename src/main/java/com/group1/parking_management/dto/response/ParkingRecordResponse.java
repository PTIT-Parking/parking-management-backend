package com.group1.parking_management.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.group1.parking_management.common.ParkingType;
import com.group1.parking_management.entity.Account;
import com.group1.parking_management.entity.ParkingCard;
import com.group1.parking_management.entity.Payment;
import com.group1.parking_management.entity.VehicleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingRecordResponse {
    private String historyId;
    private String licensePlate;
    private String identifier;
    private VehicleType vehicleType;
    private ParkingCard card;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private ParkingType type;
    private Payment payment;
    private Account staffIn;
    private Account staffOut;
}
