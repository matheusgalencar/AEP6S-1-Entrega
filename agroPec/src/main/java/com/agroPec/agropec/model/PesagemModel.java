package com.agroPec.agropec.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PesagemModel {
    private LocalDate data;
    private Double peso;
}
