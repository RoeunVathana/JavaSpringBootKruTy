package com.example.projectjava.DTO;

import com.example.projectjava.Model.Student;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeacherResponse {
    private Long id;
    private String name;
    private String gender;
    private Long age;
    private String classTeach;
    private CardResponse card;


    public TeacherResponse(Long id, String name, String gender, Long age, String classTeach,  CardResponse card) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.classTeach = classTeach;
        this.card = card;
    }

}
