package com.ucace.api.service;

import java.util.List;

import com.ucace.api.dto.UserRequestDTO;
import com.ucace.api.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO saveUser(UserRequestDTO user);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO user);

    String deleteUser(Long id);

}
