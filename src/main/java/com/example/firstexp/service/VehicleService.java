package com.example.firstexp.service;

import com.example.firstexp.model.dto.VehicleDTO;
import com.example.firstexp.model.entity.Vehicle;

public interface VehicleService {
    VehicleDTO createVehicle(VehicleDTO vehicleDTO);

    VehicleDTO update(VehicleDTO vehicleDTO);

    VehicleDTO get(String vin);

    void delete(String vin);

    Vehicle getVehicle(String vin);

    VehicleDTO addToDriver(String email, String vin);
}
