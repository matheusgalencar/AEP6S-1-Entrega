package com.agroPec.agroPec.repository;

import com.agroPec.agroPec.model.AnimalModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends MongoRepository<AnimalModel, String> {

}
