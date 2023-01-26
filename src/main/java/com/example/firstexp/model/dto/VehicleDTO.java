package com.example.firstexp.model.dto;

import com.example.firstexp.model.entity.ServiceRecord;
import com.example.firstexp.model.entity.VehicleType;
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
    VehicleType vehicleType;
    VehicleClass vehicleClass;
    List<ServiceRecord> serviceRecord;

}
