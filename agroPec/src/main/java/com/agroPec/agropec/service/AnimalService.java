package com.agroPec.agropec.service;

import com.agroPec.agropec.dto.AnimalRequestDTO;
import com.agroPec.agropec.model.AnimalModel;
import com.agroPec.agropec.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    public AnimalModel createAnimal(AnimalRequestDTO animalDTO){

        AnimalModel animal = new AnimalModel();

        animal.setBrinco(animalDTO.getBrinco());
        animal.setRaca(animalDTO.getRaca());
        animal.setPesoEntrada(animalDTO.getPesoEntrada());
        animal.setPesagens(animalDTO.getPesagens());
        animal.setVacinacoes(animalDTO.getVacinacoes());

        return animalRepository.insert(animal);
    }

    public List<AnimalModel> findAllAnimals(){
        return animalRepository.findAll();
    }

    public AnimalModel findByIdAnimal(String id){
        return animalRepository.findById(id).orElseThrow(() -> new RuntimeException("Animal não encontrado!"));
    }

    public void deleteByIdAnimal(String id){
        animalRepository.deleteById(id);
    }

    public AnimalModel updateAnimal(String id, AnimalRequestDTO animalDTO){
        AnimalModel oldAnimal = findByIdAnimal(id);

        oldAnimal.setBrinco(animalDTO.getBrinco());
        oldAnimal.setRaca(animalDTO.getRaca());
        oldAnimal.setPesoEntrada(animalDTO.getPesoEntrada());
        oldAnimal.setVacinacoes(animalDTO.getVacinacoes());

        return animalRepository.save(oldAnimal);
    }

}
