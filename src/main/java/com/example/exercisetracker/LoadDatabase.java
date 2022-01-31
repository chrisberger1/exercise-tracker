package com.example.exercisetracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepo, ExerciseRepository exerRepo) {
        return args -> {
            userRepo.save(new User("Chris Berger", 73, 190));
            userRepo.save(new User("Nick Berger", 70, 180));

            userRepo.findAll().forEach(user -> log.info("Preloaded " + user));

            exerRepo.save(new Exercise("Squats", Status.COMPLETED, 12, 235, 1));
            exerRepo.save(new Exercise("Bicep Curls", Status.IN_PROGRESS, 10, 40, 1));
            exerRepo.save(new Exercise("Pullups", Status.IN_PROGRESS, 8, null, 2));
            exerRepo.save(new Exercise("Bench Press", Status.IN_PROGRESS, 10, 145, 2));

            exerRepo.findAll().forEach(exercise -> log.info("Preloaded " + exercise));
        };
    }
}