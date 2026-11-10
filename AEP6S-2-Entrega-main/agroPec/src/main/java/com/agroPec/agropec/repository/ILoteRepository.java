package com.agroPec.agropec.repository;

import com.agroPec.agropec.model.LoteModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILoteRepository extends MongoRepository<LoteModel, Long> {
    Optional<LoteModel> findByCodigoLote(String codigoLote);
}