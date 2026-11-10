package com.agroPec.agropec.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Document(collection = "lotes")
public class LoteModel {
    @Id
    private Long id;

    private String codigoLote;
    private String descricao;
    private LocalDate dataEntrada;
    private Long quantidadeAnimais;

    private List<AnimalLoteModel> animais;

    private Double valorLoteCompra;

    private String status;


}
