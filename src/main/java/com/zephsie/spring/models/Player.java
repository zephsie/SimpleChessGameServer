package com.zephsie.spring.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "player", schema = "structure")
public class Player {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 16, message = "Name must be between 2 and 30 characters")
    private String name;

    @Column(name = "wins")
    private int wins;

    public Player() {
    }

    public Player(String name, Integer wins) {
        this.name = name;
        this.wins = wins;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }
}