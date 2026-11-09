package com.agroPec.agropec.service;

import com.agroPec.agropec.dto.AnimalLoteDTO;
import com.agroPec.agropec.dto.AnimalRequestDTO;
import com.agroPec.agropec.model.AnimalModel;
import com.agroPec.agropec.model.PesagemModel;
import com.agroPec.agropec.model.enums.EStatusAnimal;
import com.agroPec.agropec.repository.IAnimalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalService {
    private final IAnimalRepository iAnimalRepository;

    public AnimalService(IAnimalRepository iAnimalRepository){
        this.iAnimalRepository = iAnimalRepository;
    }

    public AnimalModel createAnimal(AnimalRequestDTO animalDTO){

        AnimalModel animal = new AnimalModel();

        animal.setBrinco(animalDTO.getBrinco());
        animal.setRaca(animalDTO.getRaca());
        animal.setPesoEntrada(animalDTO.getPesoEntrada());
        animal.setPesagens(animalDTO.getPesagens());
        animal.setVacinacoes(animalDTO.getVacinacoes());
        animal.setStatus(EStatusAnimal.ATIVO);
        return iAnimalRepository.insert(animal);
    }

    public AnimalModel createAnimal(AnimalLoteDTO animalDTO) {

        AnimalModel animal = new AnimalModel();

        animal.setBrinco(animalDTO.getBrinco());
        animal.setRaca(animalDTO.getRaca());
        animal.setPesoEntrada(animalDTO.getPesoEntrada());

        return iAnimalRepository.insert(animal);
    }

    public List<AnimalModel> findAllAnimals(){
        return iAnimalRepository.findAll();
    }

    public AnimalModel findByIdAnimal(String id){
        return iAnimalRepository.findById(id).orElseThrow(() -> new RuntimeException("Animal não encontrado!"));
    }
    public AnimalModel buscarPorBrinco(String brincoAnimal){
        return iAnimalRepository.buscarPorBrinco(brincoAnimal).orElseThrow(() -> new RuntimeException("Animal não encontrado"));
    }

    public void deleteByIdAnimal(String id){
        iAnimalRepository.deleteById(id);
    }

    public AnimalModel updateAnimal(String id, AnimalRequestDTO animalDTO){
        AnimalModel oldAnimal = findByIdAnimal(id);

        oldAnimal.setBrinco(animalDTO.getBrinco());
        oldAnimal.setRaca(animalDTO.getRaca());
        oldAnimal.setPesoEntrada(animalDTO.getPesoEntrada());
        oldAnimal.setVacinacoes(animalDTO.getVacinacoes());

        return iAnimalRepository.save(oldAnimal);
    }
    public AnimalModel pesarAnimal(Double novoPeso, String brincoAnimal){
        AnimalModel animal = buscarPorBrinco(brincoAnimal);

        PesagemModel novaPesagem = new PesagemModel();

        novaPesagem.setData(LocalDate.now());
        novaPesagem.setPeso(novoPeso);

        if (animal.getPesagens() == null){
            animal.setPesagens(new ArrayList<>());
        }

        animal.getPesagens().add(novaPesagem);

        return iAnimalRepository.save(animal);
    }

}
