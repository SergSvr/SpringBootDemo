package com.example.firstexp.model.entity;

import com.example.firstexp.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "drivers")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    Long id;
    @OneToMany(cascade = CascadeType.ALL)
    List<Vehicle> vehicleList;
    String name;
    String surname;
    @Column
    String email;
    @Column(name = "record_state")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    Status status;

    @Column(name = "prev_record")
    @JsonIgnore
    Long prev;

    @CreationTimestamp
    @JsonIgnore
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    @JsonIgnore
    LocalDateTime updatedAt;
}
