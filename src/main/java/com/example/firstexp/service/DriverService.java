package com.example.firstexp.service;

import com.example.firstexp.model.dto.DriverDTO;
import com.example.firstexp.model.entity.Driver;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;

public interface DriverService {
    DriverDTO createDriver(DriverDTO driverDTO);

    DriverDTO update(DriverDTO driverDTO);

    DriverDTO get(String email);

    void delete(String email);

    Driver getDriver(String email);

    ModelMap getDriversPaged(Integer page, Integer perPage, String sort, Sort.Direction order);
}
