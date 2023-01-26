package com.example.firstexp.controllers;

import com.example.firstexp.model.dto.DriverDTO;
import com.example.firstexp.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    public DriverDTO createDriver(@RequestBody DriverDTO userDTO) {
        return driverService.createDriver(userDTO);
    }

    @PutMapping
    public ResponseEntity<DriverDTO> updateDriver(@RequestBody DriverDTO userDTO) {
        return ResponseEntity.ok(driverService.update(userDTO));
    }


    @GetMapping
    public ResponseEntity<DriverDTO> getDriver(@RequestParam String email) {
        return ResponseEntity.ok(driverService.get(email));
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteDriver(@RequestParam String email) {
        driverService.delete(email);
        return ResponseEntity.ok().build();
    }


}
