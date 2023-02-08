package com.example.firstexp.model.dto;

import com.example.firstexp.model.enums.VehicleClass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleDTO {
    String vin;
    VehicleTypeDTO vehicleType;
    VehicleClass vehicleClass;
    List<ServiceRecordDTO> serviceRecord;

}