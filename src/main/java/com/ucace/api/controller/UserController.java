package com.ucace.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ucace.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.ucace.api.dto.UserRequestDTO;
import com.ucace.api.dto.UserResponseDTO;
import com.ucace.api.response.ApiResponseDTO;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Save User", description = "Create a new user")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> saveUser(@Valid @RequestBody UserRequestDTO user) {
        UserResponseDTO savedUser = userService.saveUser(user);
        ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>(
                true,
                "User created Successfully",
                savedUser,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get All Users", description = "Retrieve a list of all users")
    public ResponseEntity<ApiResponseDTO<Page<UserResponseDTO>>> getAllUsers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue = "userName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserResponseDTO> getAllUsers = userService.getAllUsers(pageable);
        ApiResponseDTO<Page<UserResponseDTO>> response = new ApiResponseDTO<>(
                true,
                "Get all Users",
                getAllUsers,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    @Operation
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getUserById(@PathVariable Long id) {
        UserResponseDTO getUserById = userService.getUserById(id);
        ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Get userby id successfully",
                getUserById,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update User", description = "Update an existing user by its ID")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateUser(@PathVariable Long id,
            @Valid @RequestBody UserRequestDTO user) {
        UserResponseDTO updateUser = userService.updateUser(id, user);
        ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>(
                true,
                "User Updated Successfully",
                updateUser,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete User", description = "Delete a user by its ID")
    public ResponseEntity<ApiResponseDTO<String>> deleteUser(@PathVariable Long id) {
        String deleteRole = userService.deleteUser(id);
        ApiResponseDTO<String> response = new ApiResponseDTO<>(
                true,
                "User Deleted Successfully",
                deleteRole,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search Users", description = "Search users by username or email")
    public ResponseEntity<ApiResponseDTO<Page<UserResponseDTO>>> searchUsers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "userName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UserResponseDTO> searchUsers = userService.searchUsers(keyword, pageable);

        ApiResponseDTO<Page<UserResponseDTO>> response = new ApiResponseDTO<>(
                true,
                "Users retrieved successfully",
                searchUsers,
                LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

}