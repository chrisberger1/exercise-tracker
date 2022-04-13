package com.example.exercisetracker.models.assemblers;

import com.example.exercisetracker.enums.Status;
import com.example.exercisetracker.controller.ExerciseController;
import com.example.exercisetracker.models.Exercise;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ExerciseModelAssembler implements RepresentationModelAssembler<Exercise, EntityModel<Exercise>> {

    @Override
    public EntityModel<Exercise> toModel(Exercise exercise) {

        //Unconditional links to single-item resource and aggregate root

        EntityModel<Exercise> exerciseModel = EntityModel.of(exercise,
                linkTo(methodOn(ExerciseController.class).one(exercise.getId())).withSelfRel(),
                linkTo(methodOn(ExerciseController.class).all()).withRel("exercises"));

        //Conditional links based on the state of the exercise

        if (exercise.getStatus() == Status.IN_PROGRESS) {
            exerciseModel.add(linkTo(methodOn(ExerciseController.class).cancel(exercise.getId())).withRel("cancel"));
            exerciseModel.add(linkTo(methodOn(ExerciseController.class).complete(exercise.getId())).withRel("complete"));
        }

        return exerciseModel;
    }
}
