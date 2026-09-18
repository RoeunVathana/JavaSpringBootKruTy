package com.example.projectjava.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MajorResponse {
    private Long id;
    private String name;
    private String description;
    private List<StudentResponse> studentResponse;
}
