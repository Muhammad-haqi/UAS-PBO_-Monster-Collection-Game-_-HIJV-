package com.game.projekgame.controller;

import com.game.projekgame.dto.ApiResponse;
import com.game.projekgame.dto.AuthResponse;
import com.game.projekgame.dto.LoginRequest;
import com.game.projekgame.dto.RegisterRequest;
import com.game.projekgame.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * POST /api/auth/register
     * Dipanggil dari RegisterController.java di frontend
     * Body: { "username": "haqi", "password": "123456" }
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registrasi berhasil!", response));
    }

    /**
     * POST /api/auth/login
     * Dipanggil dari LoginController.java di frontend
     * Body: { "username": "haqi", "password": "123456" }
     * Response: { token, username, newPlayer }
     *   → jika newPlayer=true, frontend navigasi ke StarterMonster.fxml
     *   → jika newPlayer=false, frontend navigasi ke Dashboard.fxml
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login berhasil!", response));
    }
}
