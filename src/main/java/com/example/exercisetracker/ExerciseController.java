package com.example.exercisetracker;

import org.apache.coyote.Response;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseModelAssembler assembler;

    ExerciseController(ExerciseRepository exerciseRepository, ExerciseModelAssembler assembler) {
        this.exerciseRepository = exerciseRepository;
        this.assembler = assembler;
    }

    @GetMapping("/exercises")
    CollectionModel<EntityModel<Exercise>> all() {
        List<EntityModel<Exercise>> exercises = exerciseRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(exercises, //
                linkTo(methodOn(ExerciseController.class).all()).withSelfRel());
    }

    @GetMapping("/exercises/{id}")
    EntityModel<Exercise> one(@PathVariable Long id) {
        Exercise exercise = exerciseRepository.findById(id) //
                .orElseThrow(() -> new ExerciseNotFoundException(id));

        return assembler.toModel(exercise);
    }

    @PostMapping("/exercises")
    ResponseEntity<EntityModel<Exercise>> newExercise(@RequestBody Exercise exercise) {
        exercise.setStatus(Status.IN_PROGRESS);
        Exercise newExercise = exerciseRepository.save(exercise);

        return ResponseEntity //
                .created(linkTo(methodOn(ExerciseController.class).one(newExercise.getId())).toUri()) //
                .body(assembler.toModel(newExercise));
    }

    @DeleteMapping("/exercises/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id) {

        Exercise exercise = exerciseRepository.findById(id) //
                .orElseThrow(() -> new ExerciseNotFoundException(id));

        if (exercise.getStatus() == Status.IN_PROGRESS) {
            exercise.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(exerciseRepository.save(exercise)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't cancel an exercise that is in the " + exercise.getStatus() + " status"));
    }

    @PutMapping("/exercises/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id) {

        Exercise exercise = exerciseRepository.findById(id) //
                .orElseThrow(() -> new ExerciseNotFoundException(id));

        if (exercise.getStatus() == Status.IN_PROGRESS) {
            exercise.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(exerciseRepository.save(exercise)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't complete an exercise that is in the " + exercise.getStatus() + " status"));
    }
}
