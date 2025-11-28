package com.fitnesslife.fitness.model;


import jakarta.persistence.*;
@Entity
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gostaFrutas;
    private String frutasNaoAgrada;
    private String gostaVerduras;
    private String verdurasNaoAgrada;
    private String alergias;

    @Transient
    private String mensagemIA;

    public Object getGostaVerduras() {
        return null;
    }

    public Object getGostaFrutas() {
        return null;
    }

    public void setMensagemIA(String s) {
    }
}
