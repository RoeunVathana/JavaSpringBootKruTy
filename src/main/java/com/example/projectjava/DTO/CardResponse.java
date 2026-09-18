package com.example.projectjava.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {
    private Long id;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String code;
}
