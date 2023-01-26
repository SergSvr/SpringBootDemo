package com.example.firstexp.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "messages")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Messages {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    LocalDateTime date;
    String sender;
    String text;

}
