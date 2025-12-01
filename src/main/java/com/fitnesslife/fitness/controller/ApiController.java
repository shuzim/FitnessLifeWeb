package com.fitnesslife.fitness.controller;

import com.fitnesslife.fitness.model.Pergunta;
import com.fitnesslife.fitness.model.Usuario;
import com.fitnesslife.fitness.repository.PerguntaRepository;
import com.fitnesslife.fitness.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        logger.info("Tentando criar usuário com email: {}", usuario.getEmail());
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            logger.warn("Email já existe: {}", usuario.getEmail());
            return ResponseEntity.badRequest().body(null);
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario savedUsuario = usuarioRepository.save(usuario);
        logger.info("Usuário criado com sucesso: {}", usuario.getEmail());
        return ResponseEntity.ok(savedUsuario);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        logger.info("Listando todos os usuários");
        List<Usuario> usuarios = usuarioRepository.findAll();

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        logger.info("Buscando usuário com ID: {}", id);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Usuário não encontrado com ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        logger.info("Tentando autenticar usuário com email: {}", loginRequest.getEmail());
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());

        if (usuarioOpt.isPresent() && passwordEncoder.matches(loginRequest.getSenha(), usuarioOpt.get().getSenha())) {
            logger.info("Autenticação bem-sucedida para email: {}", loginRequest.getEmail());

            // Cria autenticação com ROLE_USER
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            usuarioOpt.get().getEmail(),
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_USER"))
                    );

            // Salva autenticação no SecurityContext
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authToken);

            // Garante que o SecurityContext vai para a sessão HTTP
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", context);

            return ResponseEntity.ok("Login bem-sucedido");
        } else {
            logger.warn("Credenciais inválidas para email: {}", loginRequest.getEmail());
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }


    @PostMapping("/debug")
    public String debugKeys() {
        String googleKey = System.getenv("GOOGLE_API_KEY");
        String geminiKey = System.getenv("GEMINI_API_KEY");
        return "teste" + "GOOGLE_API_KEY=" + googleKey + " | GEMINI_API_KEY=" + geminiKey;

    }


    public static class LoginRequest {
        private String email;
        private String senha;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }


    @PostMapping("/perguntas")
    public ResponseEntity<?> salvarPerguntas(@RequestBody Map<String, String> respostas,
                                             Authentication authentication) {  // ← INJETAR AQUI

        logger.info("Salvando preferências alimentares para usuário autenticado");

        // 1 — Pega o email do usuário logado (vem do SecurityContext)
        String emailLogado = authentication.getName(); // ← email do usuário logado
        Usuario usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Long usuarioId = usuario.getId();

        // 2 — Pega os dados do corpo da requisição
        String gostaFrutas = respostas.get("gostaFrutas");
        String frutasNaoAgrada = respostas.getOrDefault("frutasNaoAgrada", "");
        String gostaVerduras = respostas.get("gostaVerduras");
        String verdurasNaoAgrada = respostas.getOrDefault("verdurasNaoAgrada", "");
        String alergias = respostas.getOrDefault("alergias", "");

        // Validação básica
        if (gostaFrutas == null || gostaVerduras == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "Responda todas as perguntas obrigatórias"));
        }

        // 3 — Busca se já existe preferência desse usuário
        Optional<Pergunta> existenteOpt = perguntaRepository.findByUsuarioId(usuarioId);

        Pergunta perguntaSalvada;

        if (existenteOpt.isPresent()) {
            // Atualiza
            Pergunta existente = existenteOpt.get();
            existente.setGostaFrutas(gostaFrutas);
            existente.setFrutasNaoAgrada(frutasNaoAgrada);
            existente.setGostaVerduras(gostaVerduras);
            existente.setVerdurasNaoAgrada(verdurasNaoAgrada);
            existente.setAlergias(alergias);
            perguntaSalvada = perguntaRepository.save(existente);
        } else {
            // Cria novo
            Pergunta nova = new Pergunta();
            nova.setUsuario(usuario);
            nova.setGostaFrutas(gostaFrutas);
            nova.setFrutasNaoAgrada(frutasNaoAgrada);
            nova.setGostaVerduras(gostaVerduras);
            nova.setVerdurasNaoAgrada(verdurasNaoAgrada);
            nova.setAlergias(alergias);
            perguntaSalvada = perguntaRepository.save(nova);
        }

        return ResponseEntity.ok(Map.of(
                "mensagem", "Preferências salvas com sucesso!",
                "atualizado", true
        ));
    }

    @GetMapping("/perguntas/ultimas")
    public ResponseEntity<?> buscarPreferenciasUsuarioLogado(Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Optional<Pergunta> perguntaOpt = perguntaRepository.findByUsuarioId(usuario.getId());

        if (perguntaOpt.isPresent()) {
            Pergunta p = perguntaOpt.get();
            return ResponseEntity.ok(Map.of(
                    "gostaFrutas", p.getGostaFrutas(),
                    "frutasNaoAgrada", p.getFrutasNaoAgrada() != null ? p.getFrutasNaoAgrada() : "",
                    "gostaVerduras", p.getGostaVerduras(),
                    "verdurasNaoAgrada", p.getVerdurasNaoAgrada() != null ? p.getVerdurasNaoAgrada() : "",
                    "alergias", p.getAlergias() != null ? p.getAlergias() : ""
            ));
        } else {
            // Retorna valores padrão se ainda não respondeu
            return ResponseEntity.ok(Map.of(
                    "gostaFrutas", "Sim",
                    "frutasNaoAgrada", "",
                    "gostaVerduras", "Sim",
                    "verdurasNaoAgrada", "",
                    "alergias", ""
            ));
        }
    }
}









