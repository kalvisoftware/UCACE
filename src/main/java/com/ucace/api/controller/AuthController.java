package com.ucace.api.controller;

import javax.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ucace.api.dto.LoginRequestDTO;
import com.ucace.api.dto.LoginResponseDTO;
import com.ucace.api.dto.RegisterRequestDTO;
import com.ucace.api.dto.UserResponseDTO;
import com.ucace.api.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        UserResponseDTO registeredUser = authService.registerUser(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        // Implement the login logic here
        LoginResponseDTO loginResponse = authService.loginUser(loginRequestDTO);
        return ResponseEntity.ok(loginResponse);
    }
}
