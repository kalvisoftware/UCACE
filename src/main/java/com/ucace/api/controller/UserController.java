package com.ucace.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ucace.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.ucace.api.dto.UserRequestDTO;
import com.ucace.api.dto.UserResponseDTO;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<UserResponseDTO> saveUser(@Valid @RequestBody UserRequestDTO user) {
        UserResponseDTO savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    @Operation(summary = "Get All Users", description = "Retrieve a list of all users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> getAllUsers = userService.getAllUsers();
        return ResponseEntity.ok(getAllUsers);
    }

    @GetMapping("{id}")
    @Operation
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO getUserById = userService.getUserById(id);
        return ResponseEntity.ok(getUserById);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update User", description = "Update an existing user by its ID")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO user) {
        UserResponseDTO updateUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete User", description = "Delete a user by its ID")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String deleteRole = userService.deleteUser(id);
        return ResponseEntity.ok(deleteRole);
    }

}