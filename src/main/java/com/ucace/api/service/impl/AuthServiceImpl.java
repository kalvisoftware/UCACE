package com.ucace.api.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ucace.api.dto.ChangePasswordRequestDTO;
import com.ucace.api.dto.LoginRequestDTO;
import com.ucace.api.dto.LoginResponseDTO;
import com.ucace.api.dto.RefreshTokenRequestDTO;
import com.ucace.api.dto.RefreshTokenResponseDTO;
import com.ucace.api.dto.RegisterRequestDTO;
import com.ucace.api.dto.UserResponseDTO;
import com.ucace.api.entity.Role;
import com.ucace.api.entity.User;
import com.ucace.api.exception.InvalidPasswordException;
import com.ucace.api.exception.ResourceAlreadyExistsException;
import com.ucace.api.exception.ResourceNotFoundException;
import com.ucace.api.repository.RoleRepository;
import com.ucace.api.repository.UserRepository;
import com.ucace.api.security.JwtUtil;
import com.ucace.api.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
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

    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {

        User user = userRepository.findByUserName(loginRequestDTO.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isPasswordMatched = passwordEncoder.matches(
                loginRequestDTO.getPassword(),
                user.getPassword());

        if (!isPasswordMatched) {
            throw new RuntimeException("Invalid Username or Password");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUserName());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserName());
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setAccessToken(accessToken);
        loginResponseDTO.setRefreshToken(refreshToken);
        return loginResponseDTO;
    }

    @Override
    public UserResponseDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userRepository.findByUserName(currentUserName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return convertToDTO(user);
    }

    @Override
    public String changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        boolean isPasswordMatch = passwordEncoder.matches(changePasswordRequestDTO.getOldPassword(),
                user.getPassword());

        if (!isPasswordMatch) {
            throw new InvalidPasswordException("Old Password is Incorrect");
        }

        if (passwordEncoder.matches(
                changePasswordRequestDTO.getNewPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "New password should be different from old password");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequestDTO.getNewPassword()));
        user.setUpdatedDate(LocalDateTime.now());
        userRepository.save(user);
        return "Password changed successfully";
    }

    @Override
    public RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refreshToken'");
    }

}
