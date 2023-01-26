package com.example.firstexp.service;

import com.example.firstexp.model.dto.DriverDTO;
import com.example.firstexp.model.entity.Driver;

public interface DriverService {
    DriverDTO createDriver(DriverDTO driverDTO);

    DriverDTO update(DriverDTO driverDTO);

    DriverDTO get(String email);

    void delete(String email);

    Driver getDriver(String email);

}
