package com.example.firstexp.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VehicleClass {
    A("Мотоцикл"),
    B("Легковой"),
    C("Грузовой");

    private final String description;
}