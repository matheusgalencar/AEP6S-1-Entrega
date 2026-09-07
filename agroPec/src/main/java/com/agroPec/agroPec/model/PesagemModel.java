package com.agroPec.agroPec.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PesagemModel {
    private LocalDate data;
    private Double peso;
}
