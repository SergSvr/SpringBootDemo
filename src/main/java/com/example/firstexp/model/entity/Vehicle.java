package com.example.firstexp.model.entity;

import com.example.firstexp.model.enums.VehicleClass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "vehicles")
public class Vehicle{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String vin;
    @OneToOne
    VehicleType vehicleType;
    @Column(name="vehicle_class")
    @Enumerated(EnumType.STRING)
    VehicleClass vehicleClass;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    List<ServiceRecord> serviceRecord;
}
