package com.example.firstexp.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    static int current_id = 0;
    public int id;
    public List<Car> carList = new ArrayList<>();

    @Getter
    @Setter
    public String name;
    @Getter
    @Setter
    public String surname;

    public void addCar(Car car) {
        this.carList.add(car);
    }

    public void deleteCar(Car car) {
        this.carList.remove(car);
    }

    public String toString() {
        return "driver " + this.id + ' ' + this.surname + ' ' + this.name;
    }

    public Driver(String name, String surname) {
        this.name = name;
        this.surname = surname;
        Driver.current_id += 1;
        this.id = current_id;
    }

    public String getCars() {
        StringBuilder result = new StringBuilder("Car List:\n");
        for (Car car : carList) {
            result.append(car).append("\n");
        }
        return result.toString();
    }
}
