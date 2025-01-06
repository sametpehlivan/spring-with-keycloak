package com.sametp.example.tutorial;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TutorialService {
    private final TutorialRepository tutorialRepository;
    public List<Tutorial> findAll(){
        return tutorialRepository.findAll();
    }
    public Tutorial findById(Long id){
        return tutorialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tutorial Not Found With ID : " + id));

    }
    public Tutorial create(Tutorial tutorial){
        return tutorialRepository.save(
                Tutorial.builder()
                        .description(tutorial.getDescription())
                        .title(tutorial.getTitle())
                        .published(false)
                        .build()
        );
    }
    private void validate(Tutorial tutorial){
        if (
                Objects.isNull(tutorial) || Objects.isNull(tutorial.getId()) ||
                        !tutorialRepository.existsById(tutorial.getId())
        ) throw new EntityNotFoundException("Tutorial Not Found With ID : " + tutorial.getId());
    }
    public Tutorial update(Tutorial tutorial){
        validate(tutorial);
        return tutorialRepository.save(tutorial);
    }
    public Tutorial delete(Tutorial tutorial){
        validate(tutorial);
        tutorialRepository.deleteById(tutorial.getId());
        return tutorial;
    }

}
