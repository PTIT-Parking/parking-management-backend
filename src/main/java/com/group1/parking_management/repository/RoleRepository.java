package com.group1.parking_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group1.parking_management.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);
}
