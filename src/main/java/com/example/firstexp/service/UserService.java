package com.example.firstexp.service;

public interface UserService {
    String getDrivers();

    String createDriver(String name, String surname);

    String deleteDriver(int id);

    String readDriver(int id);

    String updateDriver(int id, String name, String surname);

    String addCar(int driver, String name);

    String updateCar(int driver, String name);

    String deleteCar(int carid);

    String getCars(int driver);
}
