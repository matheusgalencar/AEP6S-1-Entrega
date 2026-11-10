package com.agroPec.agropec.service;

import com.agroPec.agropec.dto.VendaLoteDTO;
import com.agroPec.agropec.model.AnimalModel;
import com.agroPec.agropec.model.AnimalVenda;
import com.agroPec.agropec.model.LoteModel;
import com.agroPec.agropec.model.VendaModel;
import com.agroPec.agropec.model.enums.EStatusAnimal;
import com.agroPec.agropec.repository.IAnimalRepository;
import com.agroPec.agropec.repository.ILoteRepository;
import com.agroPec.agropec.repository.IVendaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {
    private final IAnimalRepository animalRepository;
    private final ILoteRepository loteRepository;
    private final IVendaRepository vendaRepository;

    public VendaService(IAnimalRepository animalRepository, ILoteRepository loteRepository, IVendaRepository iVendaRepository){
        this.animalRepository = animalRepository;
        this.loteRepository = loteRepository;
        this.vendaRepository = iVendaRepository;
    }

    public VendaModel venderLote(VendaLoteDTO loteVendido){
        LoteModel loteAtual = loteRepository.findByCodigoLote(loteVendido.getCodigoLote())
                .orElseThrow(() -> new RuntimeException("Lote nao encontrado"));

        List<AnimalVenda> animaisVendidos = new ArrayList<>();

        for(String brinco : loteVendido.getBrincosAnimais()){
            AnimalModel animal = animalRepository.findByBrinco(brinco)
                    .orElseThrow(() -> new RuntimeException("Animal Nao encontrado"));

            boolean pertenceAoLote = loteAtual.getAnimais().stream().anyMatch(a -> a.getAnimalId().equals(animal.getId()));

            if (!pertenceAoLote){
                throw new RuntimeException("Animal " + brinco + " nao pertence ao lote informado");
            }

            if(animal.getStatus() != EStatusAnimal.ATIVO){
                throw new RuntimeException("Animal " +  brinco + "ja foi vendido");
            }

            animal.setStatus(EStatusAnimal.VENDIDO);
            animalRepository.save(animal);

            AnimalVenda animalvenda = new AnimalVenda();
            animalvenda.setAnimalId(animal.getId());
            animalvenda.setBrincoAnimal(animal.getBrinco());
            animalvenda.setPesoAnimalVenda(animal.getPesagens() != null &&
                    !animal.getPesagens().isEmpty() ? animal.getPesagens().getLast().getPeso()
                    : animal.getPesoEntrada());
            animaisVendidos.add(animalvenda);
        }

        VendaModel venda = new VendaModel();
        venda.setCodigoLote(loteAtual.getCodigoLote());
        venda.setComprador(loteVendido.getComprador());
        venda.setDataVenda(LocalDate.now());
        venda.setAnimais(animaisVendidos);
        venda.setQuantidadeAnimais((long) animaisVendidos.size());
        venda.setTipoNegociacao(loteVendido.getTipoNegociacao());
        venda.setValorPorArroba(loteVendido.getValorPorArroba());
        venda.setValorTotalVenda(loteVendido.getValorTotalVenda());
        venda.setObservacao(loteVendido.getObservacao());

        boolean todosVendidos = loteAtual.getAnimais().stream().allMatch(
                a -> animalRepository.findById(a.getAnimalId())
                        .map(animal -> animal.getStatus() == EStatusAnimal.VENDIDO)
                        .orElse(false)
        );
        if(todosVendidos){
            loteAtual.setStatus("VENDIDO");
            loteRepository.save(loteAtual);
        }
        return vendaRepository.save(venda);
    }

    public List<VendaModel> listarVendas(){
        return vendaRepository.findAll();
    }

    public Optional<VendaModel> listarVendaCodigo(String codigoVenda){
        return vendaRepository.findByCodigoVenda(codigoVenda);
    }

    public Optional<VendaModel> listarVendaId(String id){
        return vendaRepository.findById(id);
    }
}
