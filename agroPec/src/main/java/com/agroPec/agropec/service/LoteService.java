package com.agroPec.agropec.service;

import com.agroPec.agropec.dto.AnimalLoteDTO;
import com.agroPec.agropec.dto.LoteRequestDTO;
import com.agroPec.agropec.model.AnimalLoteModel;
import com.agroPec.agropec.model.AnimalModel;
import com.agroPec.agropec.model.LoteModel;
import com.agroPec.agropec.repository.ILoteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoteService {
    private final ILoteRepository loteRepository;
    private final AnimalService animalService;

    public LoteService(ILoteRepository loteRepository, AnimalService animalService){
        this.loteRepository = loteRepository;
        this.animalService = animalService;
    }

    public LoteModel cadastrarLote(LoteRequestDTO dto){
        LoteModel lote = new LoteModel();

        lote.setCodigoLote(dto.getCodigoLote());
        lote.setDescricao(dto.getDescricao());
        lote.setDataEntrada(dto.getDataEntrada());
        lote.setValorLoteCompra(dto.getValorLoteCompra());
        lote.setStatus("ATIVO");

        List<AnimalLoteModel> animaisDoLote = new ArrayList<>();

        for (AnimalLoteDTO animalDTO : dto.getAnimais()){

            AnimalModel animal = animalService.createAnimal(animalDTO);

            AnimalLoteModel animalLote = new AnimalLoteModel();

            animalLote.setAnimalId(animal.getId());
            animalLote.setBrincoAnimal(animal.getBrinco());
            animalLote.setRaca(animal.getRaca());
            animalLote.setPesoAnimalCompra(animal.getPesoEntrada());

            animaisDoLote.add(animalLote);
        }
        lote.setAnimais(animaisDoLote);
        lote.setQuantidadeAnimais((long) animaisDoLote.size());

        return loteRepository.save(lote);
    }

    public List<LoteModel> listarLotes(){
        return loteRepository.findAll();
    }

    public Optional<LoteModel> listarLoteId(String id){
        return loteRepository.findById(id);
    }
    public Optional<LoteModel> buscarPorCodigo(String codigoLote){
        return loteRepository.buscarPorCodigoLote(codigoLote);
    }
}
