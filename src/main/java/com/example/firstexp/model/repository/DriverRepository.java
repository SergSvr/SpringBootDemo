package com.example.firstexp.model.repository;

import com.example.firstexp.model.entity.Driver;
import com.example.firstexp.model.entity.Vehicle;
import com.example.firstexp.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByEmailAndStatus(String email, Status status);
    Optional<Driver> findByVehicleListContaining(Vehicle vehicle);
}
