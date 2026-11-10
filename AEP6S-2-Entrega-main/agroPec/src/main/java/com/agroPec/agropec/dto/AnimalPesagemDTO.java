
package com.agroPec.agropec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalPesagemDTO {

    @NotNull(message = "O peso do animal é obrigatório")
    @Positive(message = "O peso deve ser maior que zero")
    private Double novoPeso;

    @NotBlank(message = "Informe o brinco do animal pesado")
    private String brincoAnimal;
}
