package com.example.firstexp.entity;

import lombok.Getter;
import lombok.Setter;

public class Car {
    static int current_id=1;
    @Getter
    public int id;
    @Getter
    @Setter
    public String carName;

    public Car(String carName){
        this.id=Car.current_id;
        Car.current_id+=1;
        this.carName=carName;
    }

    public String toString(){
        return "Car number "+this.id+' '+this.carName;
    }
}
