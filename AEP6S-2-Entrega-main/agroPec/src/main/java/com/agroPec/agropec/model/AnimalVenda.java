package com.agroPec.agropec.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Getter
@Setter
public class AnimalVenda {

    private String animalId;
    private String brincoAnimal;

    private Double pesoAnimalVenda;
}