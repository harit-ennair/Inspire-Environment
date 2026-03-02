package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.LoginRequest;
import com.example.inspire_environment.dto.response.AuthResponse;
import com.example.inspire_environment.entity.User;
import com.example.inspire_environment.exception.BadRequestException;
import com.example.inspire_environment.repository.UserRepository;
import com.example.inspire_environment.service.AuthService;
import com.example.inspire_environment.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadRequestException("Email ou mot de passe incorrect"));

        // Verify password with BCrypt
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Email ou mot de passe incorrect");
        }

        // Generate tokens
        String accessToken = jwtUtil.generateAccessToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        // Save refresh token to database
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        // Return response
        return new AuthResponse(
                accessToken,
                refreshToken,
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole() != null ? user.getRole().getName() : null
        );
    }

    @Override
    public void logout(String token) {
        // Validate token
        if (!jwtUtil.validateToken(token)) {
            throw new BadRequestException("Token invalide");
        }

        // Get email from token
        String email = jwtUtil.getEmailFromToken(token);

        // Find user and remove refresh token
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Utilisateur non trouvé"));

        user.setRefreshToken(null);
        userRepository.save(user);
    }
}
