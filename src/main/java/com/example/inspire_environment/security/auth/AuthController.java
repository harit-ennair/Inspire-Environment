package com.example.inspire_environment.security.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        authService.logout(extractToken(token));
        return ResponseEntity.ok("Déconnexion réussie");
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        boolean isValid = authService.validateToken(extractToken(token));
        return isValid
            ? ResponseEntity.ok("Token valide")
            : ResponseEntity.badRequest().body("Token invalide");
    }

    private String extractToken(String authHeader) {
        return authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
    }
}
