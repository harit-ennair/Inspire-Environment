package com.example.inspire_environment.service.impl;
//
//import com.example.inspire_environment.dto.request.LoginRequestDTO;
//import com.example.inspire_environment.dto.response.UserResponseDTO;
//import com.example.inspire_environment.entity.User;
//import com.example.inspire_environment.mapper.UserMapper;
//import com.example.inspire_environment.repository.UserRepository;
//import com.example.inspire_environment.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserServiceImp implements UserService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//    private final UserMapper userMapper;
//
//    @Override
//    public LoginRequestDTO login(LoginRequestDTO request) {
//
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        String token = jwtService.generateToken(user);
//
//        return new LoginResponseDTO(token, userMapper.toDto(user));
//    }
//
//    @Override
//    public UserResponseDTO getCurrentUser() {
//        User user = SecurityUtils.getCurrentUser();
//        return UserMapper.INSTANCE.toDto(user);
//    }
//}
