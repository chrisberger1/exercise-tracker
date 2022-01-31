package com.example.exercisetracker;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class User {

    private @Id @GeneratedValue Long id;
    private String name;
    private int height;
    private int weight;


    User() {}

    User(String name, int height, int weight) {

        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public int getHeight() { return this.height; }
    public int getWeight() { return this.weight; }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setHeight(int height) { this.height = height; }
    public void setWeight(int weight) { this.weight = weight; }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return Objects.equals(this.id, user.id)
                && Objects.equals(this.name, user.name)
                && Objects.equals(this.height, user.height)
                && Objects.equals(this.weight , user.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.height, this.weight);
    }

    @Override
    public String toString() {
        return "User{ " + "id=" + this.id + ", name='" + this.name
                + "', height=" + this.height
                + ", weight=" + this.weight + " }";
    }
}
