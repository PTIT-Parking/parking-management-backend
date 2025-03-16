package com.group1.parking_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group1.parking_management.entity.ParkingRecord;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, String> {
    boolean existsByCard_CardId(Integer cardId);
    boolean existsByLicensePlate(String licensePlate);
    boolean existsByIdentifier(String identifier);
}
