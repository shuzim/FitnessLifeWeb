package com.fitnesslife.fitness.repository;

import com.fitnesslife.fitness.model.RelogioData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RelogioDataRepository extends JpaRepository<RelogioData, Long> {
    List<RelogioData> findByUsuarioLocalIdOrderByDataHoraDesc(Long usuarioLocalId);

    // Para checar se já existe algum dado de relógio para o usuário
    Optional<RelogioData> findFirstByUsuarioLocalId(Long usuarioLocalId);
}
