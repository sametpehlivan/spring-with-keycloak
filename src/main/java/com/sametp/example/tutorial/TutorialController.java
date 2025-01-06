package com.sametp.example.tutorial;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutorials")
@ResponseBody
@RequiredArgsConstructor
public class TutorialController {
    private final TutorialService service;
    @GetMapping
    public List<Tutorial> getAll(){
        return service.findAll();
    }
    @GetMapping("/{id}")
    public Tutorial getById(@PathVariable(value = "id",required = true) Long id){
        return service.findById(id);
    }
    @PostMapping
    public Tutorial create(@RequestBody Tutorial tutorial){
        return service.create(tutorial);
    }
    @PutMapping
    public Tutorial update(@RequestBody Tutorial tutorial){
        return service.update(tutorial);
    }
    @DeleteMapping
    public Tutorial delete(@RequestBody Tutorial tutorial){
        return service.delete(tutorial);
    }

}
