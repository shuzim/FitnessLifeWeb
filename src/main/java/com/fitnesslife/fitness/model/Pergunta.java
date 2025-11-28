package com.fitnesslife.fitness.model;

import lombok.Data;
import jakarta.persistence.*;

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
    @JoinColumn(name = "usuario_id") // cria a FK no banco
    private Usuario usuario;

    public Object getGostaFrutas() {
        return gostaFrutas;
    }

    public Object getGostaVerduras() {
        return gostaVerduras;
    }

    public long getId() {
        return  id;
    }
}

