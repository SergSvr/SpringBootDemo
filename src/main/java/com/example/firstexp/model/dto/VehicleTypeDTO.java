package com.example.firstexp.model.dto;

import com.example.firstexp.model.enums.Manufacturer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleTypeDTO {
    Manufacturer manufacturer;
    String model;
}
