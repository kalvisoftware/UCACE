package com.ucace.api.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.ucace.api.dto.LoginRequestDTO;
import com.ucace.api.dto.LoginResponseDTO;
import com.ucace.api.dto.RegisterRequestDTO;
import com.ucace.api.dto.UserResponseDTO;

public interface AuthService {
    public UserResponseDTO registerUser(RegisterRequestDTO registerRequestDTO);

    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO);

}
