package com.game.projekgame.service;

import com.game.projekgame.dto.AuthResponse;
import com.game.projekgame.dto.LoginRequest;
import com.game.projekgame.dto.RegisterRequest;
import com.game.projekgame.entity.User;
import com.game.projekgame.exception.DuplicateResourceException;
import com.game.projekgame.repository.GameStateRepository;
import com.game.projekgame.repository.UserRepository;
import com.game.projekgame.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final GameStateRepository gameStateRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException(
                    "Username '" + request.getUsername() + "' sudah digunakan");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
        log.info("User baru terdaftar: {}", user.getUsername());

        String token = jwtUtil.generateToken(user.getUsername());

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .message("Registrasi berhasil! Pilih monster starter kamu.")
                .newPlayer(true) // tidak punya save game
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        // Spring Security validate username + password
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        boolean hasSavedGame = gameStateRepository.findByUserUsername(
                user.getUsername()).isPresent();

        String token = jwtUtil.generateToken(user.getUsername());
        log.info("User '{}' login, hasSavedGame={}", user.getUsername(), hasSavedGame);

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .message(hasSavedGame ? "Selamat datang kembali!" : "Login berhasil!")
                .newPlayer(!hasSavedGame)
                .build();
    }
}
