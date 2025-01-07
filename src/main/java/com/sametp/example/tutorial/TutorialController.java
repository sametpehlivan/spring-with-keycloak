package com.sametp.example.tutorial;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutorials")
@ResponseBody
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
public class TutorialController {
    private final TutorialService service;
    @GetMapping

    // for unauthorized  or authorized users
    public List<Tutorial> getAll(){
        return service.findAll();
    }
    //for  authorized users
    @GetMapping("/{id}")
    public Tutorial getById(@PathVariable(value = "id",required = true) Long id){
        return service.findById(id);
    }
    //for admin
    @PreAuthorize("hasRole('admin')")
    @PostMapping
    public Tutorial create(@RequestBody Tutorial tutorial){
        return service.create(tutorial);
    }
    //for manager
    @PreAuthorize("hasRole('manager')")
    @PutMapping
    public Tutorial update(@RequestBody Tutorial tutorial){
        return service.update(tutorial);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('admin') or #tutorial.owner == authentication.name")
    public Tutorial delete(@RequestBody Tutorial tutorial){
       return service.delete(tutorial);
    }

}
