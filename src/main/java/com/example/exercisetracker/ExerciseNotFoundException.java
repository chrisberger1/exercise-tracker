package com.example.exercisetracker;

public class ExerciseNotFoundException extends RuntimeException {

    ExerciseNotFoundException(Long id) {
        super("Could not find exercise with id: " + id);
    }
}
