package com.example.projectjava.Model;

import com.example.projectjava.DTO.CardResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "card")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate issueDate;
    private LocalDate expiryDate;

    @Column(unique = true)
    private String code;

    //inverse side of the relationship
    @OneToOne(mappedBy = "card")
    private Student student;

    @OneToOne(mappedBy = "card")
    private Teacher teacher;

    public Card(LocalDate issueDate, LocalDate expiryDate, String code) {
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.code = code;
    }


//    public CardResponse toResponse() {
//        return new CardResponse(id, issueDate, expiryDate, code);
//    }
}
