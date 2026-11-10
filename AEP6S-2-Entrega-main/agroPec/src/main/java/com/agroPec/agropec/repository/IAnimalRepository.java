package com.agroPec.agropec.repository;

import com.agroPec.agropec.model.AnimalModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAnimalRepository extends MongoRepository<AnimalModel, String> {
    Optional<AnimalModel> findByBrinco(String brinco);
}