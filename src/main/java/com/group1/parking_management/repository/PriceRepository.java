package com.group1.parking_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group1.parking_management.entity.Price;
import com.group1.parking_management.entity.VehicleType;

@Repository
public interface PriceRepository extends JpaRepository<Price, String> {
    Optional<Price> findByType(VehicleType type);
}
