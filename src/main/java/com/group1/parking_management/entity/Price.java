package com.group1.parking_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "price")
public class Price {
    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String priceId;
    
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private VehicleType type;
    
    @Column(nullable = false)
    private Integer dayPrice;
    
    @Column(nullable = false)
    private Integer nightPrice;
    
    @Column(nullable = false)
    private Integer monthlyPrice;
}