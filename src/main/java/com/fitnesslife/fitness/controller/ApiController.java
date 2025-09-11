package com.fitnesslife.fitness.controller;

import com.fitnesslife.fitness.model.Usuario;
import com.fitnesslife.fitness.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        logger.info("Tentando criar usuário com email: {}", usuario.getEmail());
        // Verificar se o email já existe
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            logger.warn("Email já existe: {}", usuario.getEmail());
            return ResponseEntity.badRequest().build();
        }
        Usuario savedUsuario = usuarioRepository.save(usuario);
        logger.info("Usuário criado com sucesso: {}", usuario.getEmail());
        return ResponseEntity.ok(savedUsuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        logger.info("Listando todos os usuários");
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        logger.info("Buscando usuário com ID: {}", id);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Usuário não encontrado com ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }
}