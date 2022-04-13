package com.example.exercisetracker.repository;

import com.example.exercisetracker.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
