package com.example.inspire_environment.security.auth;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);
    void logout(String token);
    boolean validateToken(String token);
}
