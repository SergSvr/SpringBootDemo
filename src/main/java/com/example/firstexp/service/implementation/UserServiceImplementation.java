package com.example.firstexp.service.implementation;

import com.example.firstexp.entity.Car;
import com.example.firstexp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.firstexp.entity.Driver;

import java.util.*;

@Slf4j
@Service
public class UserServiceImplementation implements UserService {

    public static Map<Integer, Driver> driverList = new TreeMap<>();

    @Override
    public String getDrivers() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry entry : driverList.entrySet()) {
            result.append(entry.getValue().toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public String createDriver(String name, String surname) {
        Driver driver = new Driver(name, surname);
        driverList.put(driver.id, driver);
        return "Driver created: " + driver;
    }

    @Override
    public String deleteDriver(int id) {
        driverList.remove(id);
        return "driver removed with id " + id;
    }

    @Override
    public String readDriver(int id) {
        return driverList.get(id).toString();
    }

    @Override
    public String updateDriver(int id, String name, String surname) {
        driverList.get(id).setName(name);
        driverList.get(id).setSurname(surname);
        return driverList.get(id).toString();
    }

    @Override
    public String addCar(int driver, String name) {
        Car car = new Car(name);
        driverList.get(driver).addCar(car);
        return car.toString();
    }

    @Override
    public String updateCar(int carid, String name) {
        Driver driver;
        for (Map.Entry<Integer, Driver> entry : driverList.entrySet()) {
            driver = entry.getValue();
            for (Car car : driver.carList) {
                if (car.getId() == carid) {
                    car.setCarName(name);
                    return "car updated " + car;
                }
            }
        }
        return "No car found";
    }

    @Override
    public String deleteCar(int carid) {
        Driver driver;
        for (Map.Entry<Integer, Driver> entry : driverList.entrySet()) {
            driver = entry.getValue();
            for (Car car : driver.carList) {
                if (car.getId() == carid) {
                    driver.deleteCar(car);
                    return "car deleted " + car;
                }
            }
        }
        return "No car found";
    }

    @Override
    public String getCars(int driver) {
        return driverList.get(driver).getCars();
    }

}
