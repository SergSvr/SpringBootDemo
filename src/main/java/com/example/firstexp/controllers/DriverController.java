package com.example.firstexp.controllers;

import com.example.firstexp.model.dto.DriverDTO;
import com.example.firstexp.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
@Tag(name = "Водители")
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    @Operation(summary = "создать водителя")
    public DriverDTO createDriver(@RequestBody DriverDTO userDTO) {
        return driverService.createDriver(userDTO);
    }

    @PutMapping
    @Operation(summary = "обновить водителя")
    public ResponseEntity<DriverDTO> updateDriver(@RequestBody DriverDTO userDTO) {
        return ResponseEntity.ok(driverService.update(userDTO));
    }


    @GetMapping
    @Operation(summary = "получить информацию о водителе")
    public ResponseEntity<DriverDTO> getDriver(@RequestParam String email) {
        return ResponseEntity.ok(driverService.get(email));
    }

    @DeleteMapping
    @Operation(summary = "удалить водителя")
    public ResponseEntity<HttpStatus> deleteDriver(@RequestParam String email) {
        driverService.delete(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/paged")
    @Operation(summary = "получить список водителей")
            public ModelMap getDriversPaged(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer perPage,
            @RequestParam(required = false, defaultValue = "name") String sort,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction order)
            {
            return driverService.getDriversPaged(page, perPage, sort, order);
}
}