package com.example.projectjava.Model;


import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import com.example.projectjava.DTO.StudentResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "student")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gender;
    private int age;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    //owner side of the relationship
    private Card card;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Major major;

    public Student( String name, String gender, Integer age, Card card) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.card = card;
    }

//    public StudentResponse toResponse(){
//        if (card == null) {
//            return new StudentResponse(id, name, gender, age, null);
//        }
//        return new StudentResponse(id, name, gender, age, card.toResponse());
//    }
}
