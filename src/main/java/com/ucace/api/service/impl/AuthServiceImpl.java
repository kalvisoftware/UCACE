package com.ucace.api.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ucace.api.dto.RegisterRequestDTO;
import com.ucace.api.dto.UserResponseDTO;
import com.ucace.api.entity.Role;
import com.ucace.api.entity.User;
import com.ucace.api.exception.ResourceAlreadyExistsException;
import com.ucace.api.exception.ResourceNotFoundException;
import com.ucace.api.repository.RoleRepository;
import com.ucace.api.repository.UserRepository;
import com.ucace.api.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO registerUser(RegisterRequestDTO registerRequestDTO) {

        User userEntity = convertToEntity(registerRequestDTO);

        User savedUser = userRepository.save(userEntity);

        return convertToDTO(savedUser);
    }

    private UserResponseDTO convertToDTO(User userSave) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userSave.getId());
        userResponseDTO.setUserName(userSave.getUserName());
        userResponseDTO.setEmail(userSave.getEmail());
        userResponseDTO.setMobileNo(userSave.getMobileNo());
        userResponseDTO.setStatus(userSave.getStatus());
        userResponseDTO.setRoleName(userSave.getRole().getRoleName());
        userResponseDTO.setCreatedDate(userSave.getCreatedDate());
        userResponseDTO.setUpdatedDate(userSave.getUpdatedDate());
        return userResponseDTO;
    }

    public User convertToEntity(RegisterRequestDTO registerRequestDTO) {
        // Implementation for converting DTO to entity
        User user = new User();
        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        if (userRepository.existsByUserName(registerRequestDTO.getUserName())) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }
        user.setUserName(registerRequestDTO.getUserName());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setMobileNo(registerRequestDTO.getMobileNo());
        Role role = roleRepository.findById(registerRequestDTO.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        user.setRole(role);
        user.setStatus(true);
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        return user;
    }
}
