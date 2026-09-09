package com.agroPec.agropec.dto;

import com.agroPec.agropec.model.PesagemModel;
import com.agroPec.agropec.model.VacinacaoModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnimalRequestDTO {
    @NotBlank(message = "O brinco é obrigatório!")
    private String brinco;
    @NotBlank(message = "Peso entrada obrigatório")
    @Positive(message = "O peso deve ser positivo")
    private Double pesoEntrada;

    @NotBlank(message = "A raça deve ser informada")
    private String raca;

    private List<VacinacaoModel> vacinacoes;

    private List<PesagemModel> pesagens;

}
