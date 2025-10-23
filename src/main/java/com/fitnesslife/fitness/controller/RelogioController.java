package com.fitnesslife.fitness.controller;

import com.fitnesslife.fitness.model.RelogioDataDto;
import com.fitnesslife.fitness.model.RelogioData;
import com.fitnesslife.fitness.repository.RelogioDataRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/relogio")
public class RelogioController {

    private final RelogioDataRepository repository;

    public RelogioController(RelogioDataRepository repository) {
        this.repository = repository;
    }

    // Endpoint chamado pelo relógio ao enviar dados
    @PostMapping("/dados")
    public ResponseEntity<?> receberDados(@RequestBody RelogioDataDto dto) {
        RelogioData r = new RelogioData();
        r.setUsuarioLocalId(dto.usuarioLocalId);
        r.setDeviceId(dto.deviceId);
        r.setPassos(dto.passos);
        r.setBatimentos(dto.batimentos);
        r.setDataHora(dto.dataHora == null ? OffsetDateTime.now() : dto.dataHora);
        repository.save(r);
        return ResponseEntity.ok().build();
    }

    // Endpoint para listar dados do relógio de um usuário
    @GetMapping("/dados/{usuarioId}")
    public ResponseEntity<?> listar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(repository.findByUsuarioLocalIdOrderByDataHoraDesc(usuarioId));
    }

    // Novo endpoint: chamado após o login do usuário
    @PostMapping("/verificarUsuario/{usuarioId}")
    public ResponseEntity<?> verificarOuCriarUsuario(@PathVariable Long usuarioId) {
        Optional<RelogioData> existente = repository.findFirstByUsuarioLocalId(usuarioId);

        if (existente.isPresent()) {
            return ResponseEntity.ok("Usuário já possui dados de relógio.");
        }else {
            // Caso não exista, cria um registro inicial
            RelogioData novo = new RelogioData();
            novo.setUsuarioLocalId(usuarioId);
            novo.setDeviceId("Desconhecido");
            novo.setPassos(0);
            novo.setBatimentos(0);
            novo.setDataHora(OffsetDateTime.now());
            repository.save(novo);
        }

        return ResponseEntity.ok(" Usuário registrado no sistema de relógio.");
    }
}
