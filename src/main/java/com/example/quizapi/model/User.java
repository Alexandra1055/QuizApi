package com.example.quizapi.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "Ranking", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Ranking> rankings;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
