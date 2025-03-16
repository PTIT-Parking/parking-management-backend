package com.group1.parking_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group1.parking_management.entity.ParkingRecordHistory;

@Repository
public interface ParkingRecordHistoryRepository extends JpaRepository<ParkingRecordHistory, String> {

}
