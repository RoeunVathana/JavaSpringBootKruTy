package com.example.projectjava.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long id;
    private String name;
    private String gender;
    private int age;
    private CardResponse card;
    private MajorResponse major;
    private List<SubjectResponse> subjects;

//    public StudentResponse(Long id, String name, String gender, int age,  CardResponse card) {
//        this.id = id;
//        this.name = name;
//        this.gender = gender;
//        this.age = age;
//        this.card = card;
//    }
}
