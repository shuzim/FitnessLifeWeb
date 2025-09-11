package com.fitnesslife.fitness.configsecurity;

import com.fitnesslife.fitness.repository.UsuarioRepository;
import com.fitnesslife.fitness.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**") // Desabilita CSRF apenas para endpoints da API
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/cadastro", "/api/usuarios/**", "/api/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable()) // 🔴 Desabilita o formLogin padrão do Spring Security
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(username);
            if (usuarioOpt.isEmpty()) {
                throw new UsernameNotFoundException("Usuário não encontrado: " + username);
            }
            Usuario usuario = usuarioOpt.get();
            return User
                    .withUsername(usuario.getEmail())
                    .password(usuario.getSenha())
                    .roles("USER")
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
