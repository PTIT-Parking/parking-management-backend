package com.group1.parking_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.group1.parking_management.dto.request.ParkingEntryRequest;
import com.group1.parking_management.dto.request.ParkingExitRequest;
import com.group1.parking_management.dto.response.ParkingEntryResponse;
import com.group1.parking_management.dto.response.ParkingExitResponse;

@Service
public interface ParkingService {
    ParkingEntryResponse registerEntry(ParkingEntryRequest request);
    public ParkingExitResponse processExit(ParkingExitRequest request);
    public List<ParkingEntryResponse> getAllRecordInParking();
}
