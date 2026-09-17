package com.example.projectjava.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentResponse {
    private Long id;
    private String name;
    private String gender;
    private int age;
    private CardResponse card;
    private MajorResponse major;

    public StudentResponse(Long id, String name, String gender, int age,  CardResponse card) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.card = card;
    }
}
