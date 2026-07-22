package com.ucace.api.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ucace.api.dto.ChangePasswordRequestDTO;
import com.ucace.api.dto.LoginRequestDTO;
import com.ucace.api.dto.LoginResponseDTO;
import com.ucace.api.dto.RefreshTokenRequestDTO;
import com.ucace.api.dto.RefreshTokenResponseDTO;
import com.ucace.api.dto.RegisterRequestDTO;
import com.ucace.api.dto.UserResponseDTO;
import com.ucace.api.response.ApiResponseDTO;
import com.ucace.api.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> registerUser(
            @Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        UserResponseDTO registeredUser = authService.registerUser(registerRequestDTO);
        ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Registered Successfully",
                registeredUser,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<LoginResponseDTO>> loginUser(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        // Implement the login logic here
        LoginResponseDTO loginResponse = authService.loginUser(loginRequestDTO);
        ApiResponseDTO<LoginResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Login Successfully",
                loginResponse,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getCurrentUser() {
        UserResponseDTO currentUser = authService.getCurrentUser();
        ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Logged User details",
                currentUser,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<ApiResponseDTO<String>> changePassword(
            @Valid @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) {
        String responseData = authService.changePassword(changePasswordRequestDTO);
        ApiResponseDTO<String> response = new ApiResponseDTO<>(
                true,
                "Password Changed Successfully",
                responseData,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponseDTO<RefreshTokenResponseDTO>> refreshToken(
            @Valid @RequestBody RefreshTokenRequestDTO request) {

        RefreshTokenResponseDTO responseData = authService.refreshToken(request);
        ApiResponseDTO<RefreshTokenResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Refresh token generated Successfully",
                responseData,
                LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @PostMapping("logout")
    public ResponseEntity<ApiResponseDTO<String>> logout() {
        String responseData = authService.logout();
        ApiResponseDTO<String> response = new ApiResponseDTO<>(
                true,
                "Logout Successfully",
                responseData,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
