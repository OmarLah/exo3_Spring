package com.example.exo_3.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class ContactDTO {
    private UUID id;
    private String lastname;
    private String firstname;
    private Integer age;
    private String birthDate;
}
