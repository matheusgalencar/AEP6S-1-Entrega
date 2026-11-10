package com.agroPec.agropec.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class LoteRequestDTO {
    private String codigoLote;
    private String descricao;
    private LocalDate dataEntrada;
    private Double valorLoteCompra;

    private List<AnimalLoteDTO> animais;
}
