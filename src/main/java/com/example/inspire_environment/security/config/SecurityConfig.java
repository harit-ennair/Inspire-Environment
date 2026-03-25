package com.example.inspire_environment.security.config;

import com.example.inspire_environment.security.jwt.JwtAuthenticationEntryPoint;
import com.example.inspire_environment.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
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

                        // Role & Department management
                        .requestMatchers(HttpMethod.GET, "/api/roles/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/roles/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/roles/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/roles/**").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/departments/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/departments/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/departments/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/departments/**").hasAnyRole("ADMIN")
                        
                        // Staff management
                        .requestMatchers(HttpMethod.GET, "/api/staff/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/staff/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/staff/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/staff/**").hasAnyRole("ADMIN")

                        // Students management
                        .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole("STAFF", "ADMIN", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/students/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/students/**").hasAnyRole("STAFF", "ADMIN", "STUDENT") // Students can update their own profile
                        .requestMatchers(HttpMethod.DELETE, "/api/students/**").hasAnyRole("ADMIN", "STAFF")

                        // Activities management
                        .requestMatchers(HttpMethod.GET, "/api/activities/**").hasAnyRole("STAFF", "ADMIN", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/activities/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/activities/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/activities/**").hasAnyRole("STAFF", "ADMIN")

                        // Presences management
                        .requestMatchers(HttpMethod.GET, "/api/presences/**").hasAnyRole("STAFF", "ADMIN", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/presences/**").hasAnyRole("STAFF", "ADMIN", "STUDENT")
                        .requestMatchers(HttpMethod.PUT, "/api/presences/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/presences/**").hasAnyRole("ADMIN", "STAFF")

                        // Attendances management
                        .requestMatchers(HttpMethod.GET, "/api/attendances/**").hasAnyRole("STAFF", "ADMIN", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/attendances/**").hasAnyRole("STAFF", "ADMIN", "STUDENT") // Students check-in to activities
                        .requestMatchers(HttpMethod.PUT, "/api/attendances/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/api/attendances/check-in-time/**").hasAnyRole("STAFF", "ADMIN")

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
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
