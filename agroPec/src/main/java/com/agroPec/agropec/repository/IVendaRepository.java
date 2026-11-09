package com.agroPec.agropec.repository;

import com.agroPec.agropec.model.VendaModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IVendaRepository extends MongoRepository<VendaModel, String> {
    Optional<VendaModel> buscaPorCodigoVenda(String vendaCodigo);
}
