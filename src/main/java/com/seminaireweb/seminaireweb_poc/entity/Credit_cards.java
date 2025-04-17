package com.seminaireweb.seminaireweb_poc.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "credit_cards")
public class Credit_cards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numbers;
    private String pin;
}
