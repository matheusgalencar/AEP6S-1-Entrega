package com.agroPec.agropec.repository;

import com.agroPec.agropec.model.AnimalModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends MongoRepository<AnimalModel, String> {

}
