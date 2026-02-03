package com.example.inspire_environment.security.config;

import com.example.inspire_environment.security.jwt.JwtAuthenticationEntryPoint;
import com.example.inspire_environment.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
// ===============================================================================================================
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//            .cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                    // Public endpoints
                    .requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-resources/**",
                            "/swagger-ui.html",
                            "/webjars/**",
                            "/api/auth/**"
                    ).permitAll()
                    
                    // Student endpoints
                    .requestMatchers("/api/students/me/activities").hasRole("STUDENT")
                    .requestMatchers("/api/students/me/presence").hasRole("STUDENT")
                    .requestMatchers("/api/students/me/submissions").hasRole("STUDENT")
                    .requestMatchers("/api/submissions").hasRole("STUDENT")
                    
                    // Staff endpoints
                    .requestMatchers("/api/activities/**").hasAnyRole("STAFF", "ADMIN")
                    .requestMatchers("/api/tasks/**").hasAnyRole("STAFF", "ADMIN")
                    .requestMatchers("/api/presences/**").hasAnyRole("STAFF", "ADMIN")
                    .requestMatchers("/api/attendance/**").hasAnyRole("STAFF", "ADMIN")
                    
                    // Admin only endpoints
                    .requestMatchers("/api/users/**").hasRole("ADMIN")
                    .requestMatchers("/api/roles/**").hasRole("ADMIN")
                    .requestMatchers("/api/departments/**").hasRole("ADMIN")
                    .requestMatchers("/api/staff/**").hasRole("ADMIN")
                    .requestMatchers("/api/students/**").hasRole("ADMIN")
                    
                    // All other requests require authentication
                    .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
// ===============================================================================================================
}
