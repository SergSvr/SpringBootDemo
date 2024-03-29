package com.example.firstexp.model.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceRecordDTO {
    String vin;
    Integer odometer;
    LocalDateTime date;
    String operations;
}
