package com.example.projectjava.Model;

import com.example.projectjava.DTO.TeacherResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teacher")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gender;
    private Long age;
    private String classTeach;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Card card;




    public Teacher(String name, String gender, Long age, String classTeach, Card card) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.classTeach = classTeach;
        this.card = card;
    }

    public TeacherResponse toResponse() {
        if(card == null) {
            return  new TeacherResponse(id, name, gender, age, classTeach, null);
        }
        return new TeacherResponse(
                id,
                name,
                gender,
                age,
                classTeach,
                card.toResponse()
        );
    }

}