package com.agroPec.agropec.controller;

import com.agroPec.agropec.dto.AnimalRequestDTO;
import com.agroPec.agropec.model.AnimalModel;
import com.agroPec.agropec.service.AnimalService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animais")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService){
        this.animalService = animalService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<AnimalModel>> listAnimals(){
        List<AnimalModel> animals = animalService.findAllAnimals();
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<AnimalModel> findAnimal(@PathVariable String id){
        AnimalModel animal = animalService.findByIdAnimal(id);
        return ResponseEntity.ok(animal);
    }

    @PostMapping("/create")
    public ResponseEntity<AnimalModel> createAnimal(@Valid  @RequestBody AnimalRequestDTO animal){
        AnimalModel animalCreated = animalService.createAnimal(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(animalCreated);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AnimalModel> updateAnimal(@PathVariable String id,
                                                    @RequestBody AnimalRequestDTO animal){
        return ResponseEntity.ok(animalService.updateAnimal(id, animal));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable String id){
        animalService.deleteByIdAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
