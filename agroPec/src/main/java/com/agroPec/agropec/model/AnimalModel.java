package com.agroPec.agropec.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter

@Document(collection = "animais")
public class AnimalModel {
    @Id
    private String id;

    private String brinco;
    private Double pesoEntrada;
    private String raca;

    private List<PesagemModel> pesagens;
    private List<VacinacaoModel> vacinacoes;
}
