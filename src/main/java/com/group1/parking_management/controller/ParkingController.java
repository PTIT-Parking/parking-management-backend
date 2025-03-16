package com.group1.parking_management.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group1.parking_management.dto.ApiResponse;
import com.group1.parking_management.dto.request.ParkingEntryRequest;
import com.group1.parking_management.dto.request.ParkingExitRequest;
import com.group1.parking_management.dto.response.ParkingEntryResponse;
import com.group1.parking_management.dto.response.ParkingExitResponse;
import com.group1.parking_management.service.ParkingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
public class ParkingController {
    private final ParkingService parkingService;

    @PostMapping("/entry")
    public ApiResponse<ParkingEntryResponse> registerEntry(@RequestBody ParkingEntryRequest request) {
        return ApiResponse.<ParkingEntryResponse>builder()
                .result(parkingService.registerEntry(request))
                .build();
    }

    @PostMapping("/exit")
    public ApiResponse<ParkingExitResponse> registerExit(@RequestBody ParkingExitRequest request) {
        return ApiResponse.<ParkingExitResponse>builder()
                .result(parkingService.processExit(request))
                .build();
    }
}
