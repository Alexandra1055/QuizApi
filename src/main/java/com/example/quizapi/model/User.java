package com.example.quizapi.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "User")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String username;

    @OneToMany(mappedBy = "Ranking", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Ranking> ranking;

}
