package com.example.firstexp.controllers;

import com.example.firstexp.model.dto.VehicleDTO;
import com.example.firstexp.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Tag(name = "Транспортные средства")
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    @Operation(summary = "Создание ТС")
    public VehicleDTO createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        return vehicleService.createVehicle(vehicleDTO);
    }

    @PutMapping
    @Operation(summary = "Обновление ТС")
    public ResponseEntity<VehicleDTO> updateVehicle(@RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.ok(vehicleService.update(vehicleDTO));
    }


    @GetMapping
    @Operation(summary = "получение информации о ТС")
    public ResponseEntity<VehicleDTO> getVehicleDTO(@RequestParam String vin) {
        return ResponseEntity.ok(vehicleService.get(vin));
    }

    @DeleteMapping
    @Operation(summary = "удаление ТС")
    public ResponseEntity<HttpStatus> deleteVehicle(@RequestParam String vin) {
        vehicleService.delete(vin);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addtodriver")
    @Operation(summary = "назначение ТС водителю")
    public ResponseEntity<VehicleDTO> addToDriver(@RequestParam String email, @RequestParam String vin) {
        return ResponseEntity.ok(vehicleService.addToDriver(email, vin));
    }
}