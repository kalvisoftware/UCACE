package com.ucace.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ucace.api.dto.UserRequestDTO;
import com.ucace.api.dto.UserResponseDTO;
import com.ucace.api.entity.Role;
import com.ucace.api.entity.User;
import com.ucace.api.repository.RoleRepository;
import com.ucace.api.repository.UserRepository;
import com.ucace.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserResponseDTO saveUser(UserRequestDTO user) {
        User userEntity = convertToEntity(user);
        User savedUser = userRepository.save(userEntity);
        return convertToDTO(savedUser);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> getAllUser = userRepository.findAll();
        return getAllUser.stream().map(user -> {
            return convertToDTO(user);
        }).collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        return convertToDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (existingUser != null) {
            User userEntity = updateEntity(existingUser, user);
            return convertToDTO(userRepository.save(userEntity));
        }
        return null;
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User Deleted Successfully";
    }

    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setMobileNo(user.getMobileNo());
        dto.setStatus(user.getStatus());
        dto.setRoleName(user.getRole().getRoleName());
        dto.setCreatedDate(user.getCreatedDate());
        dto.setUpdatedDate(user.getUpdatedDate());
        return dto;
    }

    private User convertToEntity(UserRequestDTO user) {
        User userEntity = new User();
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        userEntity.setMobileNo(user.getMobileNo());
        userEntity.setStatus(user.getStatus());
        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        userEntity.setRole(role);
        userEntity.setCreatedDate(LocalDateTime.now());
        userEntity.setUpdatedDate(LocalDateTime.now());
        return userEntity;
    }

    private User updateEntity(User existingUser,
            UserRequestDTO dto) {
        existingUser.setUserName(dto.getUserName());
        existingUser.setPassword(dto.getPassword());
        existingUser.setEmail(dto.getEmail());
        existingUser.setMobileNo(dto.getMobileNo());
        existingUser.setStatus(dto.getStatus());
        existingUser.setRole(roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found")));
        existingUser.setUpdatedDate(LocalDateTime.now());
        return existingUser;

    }
}
