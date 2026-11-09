package com.agroPec.agropec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalLoteDTO {
    @NotBlank(message = "Informe o brinco do animal")
    private String brinco;
    @NotBlank(message = "informe a raca do animal")
    private String raca;
    @NotNull(message = "Peso obrigatorio")
    @Positive(message = "O peso deve ser maior que zero")
    private Double pesoEntrada;
}
