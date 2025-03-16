package com.group1.parking_management.service;

import org.springframework.stereotype.Service;

import com.group1.parking_management.dto.request.ParkingEntryRequest;
import com.group1.parking_management.dto.response.ParkingEntryResponse;

@Service
public interface ParkingService {
    ParkingEntryResponse registerEntry(ParkingEntryRequest request);
}
