package com.firstproject.model;

import jakarta.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 50)
    private String name;

    // Getters

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

   // Setters

    public String setName(String name) {
        return this.name = name;
    }


}
