package com.agroPec.agroPec.service;

import com.agroPec.agroPec.model.AnimalModel;
import com.agroPec.agroPec.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    public AnimalModel createAnimal(AnimalModel animalModel){
        return animalRepository.insert(animalModel);
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

    public AnimalModel updateAnimal(String id, AnimalModel animalModel){
        AnimalModel oldAnimal = findByIdAnimal(id);

        oldAnimal.setBrinco(animalModel.getBrinco());
        oldAnimal.setRaca(animalModel.getRaca());
        oldAnimal.setPesoEntrada(animalModel.getPesoEntrada());

        return animalRepository.save(oldAnimal);
    }

}
