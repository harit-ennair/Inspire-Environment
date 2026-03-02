package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.request.LoginRequest;
import com.example.inspire_environment.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
    void logout(String token);
}
