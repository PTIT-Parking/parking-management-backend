package com.group1.parking_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

public class Admin {
    @Id
    @Column(name = "account_id")
    private String accountId;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false, length = 100)
    private String email;
}
