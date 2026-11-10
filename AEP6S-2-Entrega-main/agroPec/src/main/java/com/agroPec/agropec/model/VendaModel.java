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
@Document(collection = "vendas")
public class VendaModel {
    @Id
    private String id;

    private String codigoVenda;
    private String codigoLote;
    private LocalDate dataVenda;
    private String comprador;
    private Long quantidadeAnimais;

    private List<AnimalVenda> animais;
    private Long pesoTotal;

    private String tipoNegociacao;
    private Double valorPorArroba;
    private Double valorTotalVenda;

    private String status;
    private String observacao;
}
