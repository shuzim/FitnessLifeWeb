package com.fitnesslife.fitness.repository;

import com.fitnesslife.fitness.model.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
    Optional<Pergunta> findByUsuarioId(Long usuarioId);
}

