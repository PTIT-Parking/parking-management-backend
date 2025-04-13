package com.group1.parking_management.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group1.parking_management.entity.ActiveMonthlyRegistration;
import com.group1.parking_management.entity.Vehicle;

@Repository
public interface ActiveMonthlyRegistrationRepository extends JpaRepository<ActiveMonthlyRegistration, String> {
    @Query("""
                SELECT COUNT(a) > 0 FROM ActiveMonthlyRegistration a
                JOIN a.vehicle v
                WHERE v.licensePlate = :licensePlate
            """)
    boolean existsByLicensePlate(@Param("licensePlate") String licensePlate);

    boolean existsByVehicle(Vehicle vehicle);

    List<ActiveMonthlyRegistration> findAllByOrderByIssueDateDesc();

    List<ActiveMonthlyRegistration> findByExpirationDateBefore(LocalDateTime dateTime);
}
