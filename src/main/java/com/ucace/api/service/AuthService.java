package com.ucace.api.service;

import com.ucace.api.dto.RegisterRequestDTO;
import com.ucace.api.dto.UserResponseDTO;

public interface AuthService {
    public UserResponseDTO registerUser(RegisterRequestDTO registerRequestDTO);

}
