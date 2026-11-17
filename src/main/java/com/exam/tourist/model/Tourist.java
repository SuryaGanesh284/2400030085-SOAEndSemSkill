package com.exam.tourist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tourists")
public class Tourist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String destination;
    private Double budget;

    public Tourist() {
    }

    public Tourist(String name, String email, String destination, Double budget) {
        this.name = name;
        this.email = email;
        this.destination = destination;
        this.budget = budget;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }
}
