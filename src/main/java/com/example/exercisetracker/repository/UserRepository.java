package com.example.exercisetracker.repository;

import com.example.exercisetracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}