package com.ucace.api.service;

import com.ucace.api.dto.ChangePasswordRequestDTO;
import com.ucace.api.dto.LoginRequestDTO;
import com.ucace.api.dto.LoginResponseDTO;
import com.ucace.api.dto.RegisterRequestDTO;
import com.ucace.api.dto.UserResponseDTO;

public interface AuthService {
    public UserResponseDTO registerUser(RegisterRequestDTO registerRequestDTO);

    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO);

    public UserResponseDTO getCurrentUser();

    public String changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

}
