package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, String> {
}
