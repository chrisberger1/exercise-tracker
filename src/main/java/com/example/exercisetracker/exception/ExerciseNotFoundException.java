package com.example.exercisetracker.exception;

public class ExerciseNotFoundException extends RuntimeException {

    public ExerciseNotFoundException(Long id) {
        super("Could not find exercise with id: " + id);
    }
}
