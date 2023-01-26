package com.example.firstexp.controllers;

import com.example.firstexp.model.dto.VehicleDTO;
import com.example.firstexp.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    @PostMapping
    public VehicleDTO createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        return vehicleService.createVehicle(vehicleDTO);
    }

    @PutMapping
    public ResponseEntity<VehicleDTO> updateVehicle(@RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.ok(vehicleService.update(vehicleDTO));
    }


    @GetMapping
    public ResponseEntity<VehicleDTO> getVehicle(@RequestParam String vin) {
        return ResponseEntity.ok(vehicleService.get(vin));
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteVehicle(@RequestParam String vin) {
        vehicleService.delete(vin);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addtodriver")
    public ResponseEntity<VehicleDTO> addToDriver(@RequestParam String email, @RequestParam String vin) {
        return ResponseEntity.ok(vehicleService.addToDriver(email, vin));
    }
}
