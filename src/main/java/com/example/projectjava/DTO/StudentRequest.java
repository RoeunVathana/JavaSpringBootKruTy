package com.example.projectjava.DTO;

import com.example.projectjava.Model.Card;
import com.example.projectjava.Model.Student;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequest {

    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    @NotBlank(message = "Gender cannot be null or empty")
    private String gender;

    @NotNull(message = "Age cannot be null")
    @Min(value = 1, message = "Age must be greater than 0")
    private Integer age;

    @Valid
    @NotNull(message = "Card is required")
    private CardRequest card;

    public Student toEntity(String code) {
        Card card = new Card(this.card.getIssueDate(), this.card.getExpiryDate(), code);
        Student student = new Student(name, gender, age, card);
        card.setStudent(student);
        return student;
    }
}