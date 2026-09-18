package com.example.projectjava.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {
    @NotNull(message = "issue date is not null")
    private LocalDate issueDate;
    @NotNull(message = "expiry date is not null")
    private LocalDate expiryDate;
}
