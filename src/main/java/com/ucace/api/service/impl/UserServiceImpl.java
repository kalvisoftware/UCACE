package com.ucace.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ucace.api.dto.UserRequestDTO;
import com.ucace.api.dto.UserResponseDTO;
import com.ucace.api.entity.Role;
import com.ucace.api.entity.User;
import com.ucace.api.exception.ResourceNotFoundException;
import com.ucace.api.repository.RoleRepository;
import com.ucace.api.repository.UserRepository;
import com.ucace.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserResponseDTO saveUser(UserRequestDTO user) {
        logger.info("saving user with Username : {}", user.getUserName());
        User userEntity = convertToEntity(user);
        User savedUser = userRepository.save(userEntity);
        logger.info("User saved successfully with id : {}", savedUser.getId());
        return convertToDTO(savedUser);
    }

    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        logger.info("Fetching all users");
        Page<User> getAllUser = userRepository.findAll(pageable);
        // return getAllUser.stream().map(user -> {
        // return convertToDTO(user);
        // }).collect(Collectors.toList());
        logger.info("Total users found : {}", getAllUser.getTotalElements());
        return getAllUser.map(this::convertToDTO);
    }

    public Page<UserResponseDTO> searchUsers(String keyword, Pageable pageable) {
        logger.info("Searching all users");
        Page<User> users = userRepository.findByUserNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                keyword,
                keyword,
                pageable);
        logger.info("Total users found : {}", users.getTotalElements());
        return users.map(this::convertToDTO);
    }

    public UserResponseDTO getUserById(Long id) {
        logger.info("Fetching user with id : {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        logger.info("User found : {}", user.getUserName());
        return convertToDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO user) {
        logger.info("Updating user with id : {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        if (existingUser != null) {
            User userEntity = updateEntity(existingUser, user);
            return convertToDTO(userRepository.save(userEntity));
        }
        logger.info("User updated successfully");
        return null;
    }

    public String deleteUser(Long id) {
        logger.info("Deleting user with id : {}", id);
        userRepository.deleteById(id);
        logger.info("User deleted successfully");
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
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
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
                .orElseThrow(() -> new ResourceNotFoundException("Role not found")));
        existingUser.setUpdatedDate(LocalDateTime.now());
        return existingUser;

    }
}
