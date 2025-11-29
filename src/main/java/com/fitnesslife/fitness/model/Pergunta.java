package com.fitnesslife.fitness.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gostaFrutas;
    private String frutasNaoAgrada;
    private String gostaVerduras;
    private String verdurasNaoAgrada;
    private String alergias;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;  // FK para o usuário



}
