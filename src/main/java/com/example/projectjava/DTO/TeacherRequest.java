package com.example.projectjava.DTO;

import com.example.projectjava.Model.Card;
import com.example.projectjava.Model.Teacher;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRequest {
    @NotNull(message = "Name is required!")
    private String name;
    @NotNull(message = "Gender is required!")
    private String gender;
    @NotEmpty(message = "Age is required!")
    private Long age;
    @NotNull(message = "ClassTeach is required!")
    private String classTeach;

    @Valid
    @NotNull(message = "Card is required")
    private CardRequest cardRequest;

    public Teacher toEntity(String code) {
        Card card = new Card(cardRequest.getIssueDate(), cardRequest.getExpiryDate(), code);
        Teacher teacher =  new Teacher(name, gender, age, classTeach, card);
        card.setTeacher(teacher);
        return teacher;
    }
}
