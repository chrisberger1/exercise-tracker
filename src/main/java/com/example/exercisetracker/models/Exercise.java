package com.example.exercisetracker.models;

import com.example.exercisetracker.enums.Status;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_EXERCISE")
public class Exercise {

    private @Id @GeneratedValue Long id;

    private String description;
    private Status status;
    private int reps;
    private Integer weight;
    private int userId;
    private Date date = new Date();

    Exercise() {}

    public Exercise(String description, Status status, int reps, Integer weight, int userId) {
        this.description = description;
        this.status = status;
        this.reps = reps;
        this.weight = weight;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public int getReps() {
        return reps;
    }

    public Integer getWeight() {
        return weight;
    }

    public int getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Exercise))
            return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(this.id, exercise.id)
                && Objects.equals(this.description, exercise.description)
                && this.status == exercise.status
                && Objects.equals(this.reps, exercise.reps)
                && Objects.equals(this.weight, exercise.weight)
                && Objects.equals(this.userId, exercise.userId)
                && Objects.equals(this.date, exercise.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.description, this.status, this.reps, this.weight, this.userId, this.date);
    }

    @Override
    public String toString() {
        return "Exercise{ " + "id=" + this.id
                + ", description='" + this.description + '\''
                + ", status=" + this.status
                + ", reps=" + this.reps
                + ", weight=" + this.weight
                + ", userId=" + this.userId
                + ", date='" + this.date +"' }";
    }
}
