package com.example.inspire_environment.security.auth;

import com.example.inspire_environment.entity.User;
import com.example.inspire_environment.exception.BusinessException;
import com.example.inspire_environment.repository.UserRepository;
import com.example.inspire_environment.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService,
                           UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    // ===============================================================================================================
    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new BusinessException("Utilisateur non trouvé."));

            return createJwtResponse(user);

        } catch (BadCredentialsException ex) {
            throw new BusinessException("Email ou mot de passe invalide.");
        } catch (AuthenticationException ex) {
            throw new BusinessException("Erreur d'authentification.");
        }
    }

    @Override
    public void logout(String token) {
        // TODO: Implement token invalidation logic
    }

    @Override
    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    private JwtResponse createJwtResponse(User user) {
        String username = user.getFirstName() + " " + user.getLastName();
        String roleName = user.getRole() != null ? user.getRole().getName() : "USER";
        String token = jwtService.generateToken(user.getEmail(), roleName);
        return new JwtResponse(token, username, user.getEmail(), roleName);
    }
}
