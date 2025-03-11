package com.group1.parking_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group1.parking_management.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsByUsername(String username);
}
