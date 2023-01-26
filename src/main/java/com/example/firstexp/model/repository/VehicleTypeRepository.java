package com.example.firstexp.model.repository;

import com.example.firstexp.model.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
}
