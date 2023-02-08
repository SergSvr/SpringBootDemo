package com.example.firstexp.model.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverDTO {
    List<VehicleDTO> vehicleList;
    String name;
    String surname;
    String email;
}
