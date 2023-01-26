package com.example.firstexp.model.dto;


import com.example.firstexp.model.entity.Vehicle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverDTO {
    List<Vehicle> vehicleList;
    String name;
    String surname;
    String email;
}
