package com.example.firstexp.model.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDTO {
    LocalDateTime date;
    String sentFrom;
    String sentTo;
    String text;
}
