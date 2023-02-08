package com.example.firstexp.service.implementation;

import com.example.firstexp.exceptions.CustomException;
import com.example.firstexp.model.dto.VehicleDTO;
import com.example.firstexp.model.entity.Driver;
import com.example.firstexp.model.entity.Vehicle;
import com.example.firstexp.model.entity.VehicleType;
import com.example.firstexp.model.repository.DriverRepository;
import com.example.firstexp.model.repository.VehicleRepository;
import com.example.firstexp.model.repository.VehicleTypeRepository;
import com.example.firstexp.service.DriverService;
import com.example.firstexp.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final DriverService driverService;
    private final ObjectMapper mapper;

    @Override
    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        vehicleRepository.findByVin(vehicleDTO.getVin()).ifPresent(
                driver -> {
                    throw new CustomException("Авто с данным VIN существует", HttpStatus.BAD_REQUEST);
                }
        );
        Vehicle vehicle = mapper.convertValue(vehicleDTO, Vehicle.class);
        VehicleType temp = vehicleTypeRepository.findVehicleTypeByManufacturerAndModel(vehicleDTO.getVehicleType().getManufacturer(), vehicleDTO.getVehicleType().getModel())
                .orElse(mapper.convertValue(vehicleDTO.getVehicleType(), VehicleType.class));
        vehicle.setVehicleType(temp);
        vehicleTypeRepository.save(temp);
        Vehicle save = vehicleRepository.save(vehicle);
        return mapper.convertValue(save, VehicleDTO.class);
    }

    @Override
    public VehicleDTO update(VehicleDTO vehicleDTO) {
        Vehicle vehicle = getVehicle(vehicleDTO.getVin());
        VehicleType temp = vehicleTypeRepository.findVehicleTypeByManufacturerAndModel(vehicleDTO.getVehicleType().getManufacturer(), vehicleDTO.getVehicleType().getModel())
                .orElse(mapper.convertValue(vehicleDTO.getVehicleType(), VehicleType.class));
        vehicle.setVehicleType(temp);
        vehicle.setVehicleClass(vehicleDTO.getVehicleClass());
        vehicle.setUpdatedAt(LocalDateTime.now());
        vehicleTypeRepository.save(vehicle.getVehicleType());
        vehicleRepository.save(vehicle);
        return mapper.convertValue(vehicle, VehicleDTO.class);
    }


    @Override
    public void delete(String vin) {
        Vehicle vehicle = getVehicle(vin);
        Optional<Driver> driver = driverRepository.findByVehicleListContaining(vehicle);
        driver.ifPresent(theDriver -> {
            theDriver.getVehicleList().remove(vehicle);
            driverRepository.save(theDriver);
        });
        vehicleRepository.delete(vehicle);
    }

    @Override
    public Vehicle getVehicle(String vin) {
        return vehicleRepository.
                findByVin(vin).
                orElseThrow(() -> new CustomException("Авто с данным VIN не найден", HttpStatus.NOT_FOUND));
    }

    @Override
    public VehicleDTO get(String vin) {
        Vehicle vehicle = getVehicle(vin);
        VehicleDTO vehicleDTO = mapper.convertValue(vehicle, VehicleDTO.class);
        vehicleDTO.setServiceRecord(vehicleDTO.getServiceRecord()
                .stream()
                .map(serviceRecordDTO -> {
                    serviceRecordDTO.setVin(vin);
                    return serviceRecordDTO;
                })
                .collect(Collectors.toList()));
        return vehicleDTO;
    }

    @Override
    public VehicleDTO addToDriver(String email, String vin) {
        Driver driver = driverService.getDriver(email);
        Vehicle vehicle = getVehicle(vin);
        driver.getVehicleList().add(vehicle);
        driverRepository.save(driver);
        Vehicle save = vehicleRepository.save(vehicle);
        return mapper.convertValue(save, VehicleDTO.class);
    }
}