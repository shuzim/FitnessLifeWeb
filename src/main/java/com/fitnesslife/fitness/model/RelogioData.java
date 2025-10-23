package com.fitnesslife.fitness.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "relogio_data")
public class RelogioData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioLocalId; // id do usuário no seu sistema (opcional)
    private String deviceId;     // id do dispositivo / profile id (opcional)
    private Integer passos;
    private Integer batimentos;
    private OffsetDateTime dataHora;


}
