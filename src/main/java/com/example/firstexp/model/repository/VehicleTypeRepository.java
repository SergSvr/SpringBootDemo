package com.example.firstexp.model.repository;

import com.example.firstexp.model.entity.VehicleType;
import com.example.firstexp.model.enums.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    Optional<VehicleType> findVehicleTypeByManufacturerAndModel(Manufacturer manufacturer, String model);
}
