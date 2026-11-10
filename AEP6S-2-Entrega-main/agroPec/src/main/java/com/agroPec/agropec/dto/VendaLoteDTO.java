package com.agroPec.agropec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VendaLoteDTO {
    @NotBlank
    private String codigoLote;
    @NotBlank
    private String comprador;
    @NotEmpty
    private List<String> brincosAnimais;

    @NotBlank
    private String tipoNegociacao;

    private Double valorPorArroba;
    private Double valorTotalVenda;
    private String observacao;
}
