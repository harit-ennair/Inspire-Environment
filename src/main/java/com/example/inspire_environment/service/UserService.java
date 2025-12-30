package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.request.LoginRequestDTO;
import com.example.inspire_environment.dto.response.UserResponseDTO;

public interface UserService {

    LoginRequestDTO login(LoginRequestDTO request);

    UserResponseDTO getCurrentUser();
}
