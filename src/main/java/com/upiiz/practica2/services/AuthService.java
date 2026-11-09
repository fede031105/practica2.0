package com.upiiz.practica2.services;

import com.upiiz.practica2.dto.AuthResponse;
import com.upiiz.practica2.dto.LoginRequest;
import com.upiiz.practica2.dto.RegisterRequest;
import com.upiiz.practica2.models.Usuario;
import com.upiiz.practica2.repositories.UsuarioRepository;
import com.upiiz.practica2.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse registrar(RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(request.getRol())
                .build();

        usuarioRepository.save(usuario);
        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol().name());

        return AuthResponse.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol().name());

        return AuthResponse.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .build();
    }
}