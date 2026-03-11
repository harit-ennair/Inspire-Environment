package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.request.LoginRequest;
import com.example.inspire_environment.dto.response.AuthResponse;
import com.example.inspire_environment.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(@RequestHeader(value = "Authorization", required = false) String token) {

        if (token != null && !token.isEmpty()) {
            String jwtToken = token.replace("Bearer ", "");
            authService.logout(jwtToken);
            return ResponseEntity.ok(AuthResponse.builder()
                    .message("Déconnexion réussie")
                    .build());
        }
        return ResponseEntity.ok(AuthResponse.builder()
                .message("Déconnexion échouée")
                .build());
    }
}
