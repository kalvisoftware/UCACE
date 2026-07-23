package com.ucace.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ucace.api.dto.UserRequestDTO;
import com.ucace.api.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO saveUser(UserRequestDTO user);

    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    UserResponseDTO getUserById(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO user);

    String deleteUser(Long id);

    Page<UserResponseDTO> searchUsers(String keyword, Pageable pageable);

}
