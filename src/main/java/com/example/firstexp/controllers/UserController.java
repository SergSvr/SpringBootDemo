package com.example.firstexp.controllers;

import com.example.firstexp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/addDriver")
    public String createDrv(@RequestParam("name") String name, @RequestParam("surname") String surname) {
        return userService.createDriver(name, surname);
    }

    @PostMapping(value = "/deleteDriver")
    public String deletDrv(@RequestParam("id") int id) {
        return userService.deleteDriver(id);
    }

    @PostMapping(value = "/updateDriver")
    public String updateDriver(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("surname") String surname) {
        try {
            return userService.updateDriver(id, name, surname);
        } catch (Exception e) {
            return "No such Driver";
        }
    }

    @GetMapping(value = "/drivers")
    public String getDrivers() {
        return userService.getDrivers();
    }

    @GetMapping(value = "/drivers/{id}")
    public String getDriver(@PathVariable int id) {
        try {
            return userService.readDriver(id);
        } catch (Exception e) {
            return "No such Driver";
        }
    }
    @PostMapping(value = "/addcar")
    public String setCar(@RequestParam("id") int id, @RequestParam("name") String name) {
        try {
            return userService.addCar(id,name);
        } catch (Exception e) {
            return "No such Driver";
        }
    }
    @PostMapping(value = "/updatecar")
    public String updateCar(@RequestParam("id") int id, @RequestParam("name") String name) {
            return userService.updateCar(id,name);
    }
    @GetMapping(value = "/getcars/{id}")
    public String updateCar(@PathVariable int id) {
        try {
            return userService.getCars(id);
        } catch (Exception e) {
            return "No such Driver";
        }
    }

    @PostMapping(value = "/deletecar")
    public String deletCar(@RequestParam("id") int id) {
        return userService.deleteCar(id);
    }

}
