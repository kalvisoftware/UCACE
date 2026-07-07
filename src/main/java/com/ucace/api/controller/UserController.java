package com.ucace.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ucace.api.service.UserService;
import com.ucace.api.dto.UserRequestDTO;
import com.ucace.api.dto.UserResponseDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> saveUser(@Valid @RequestBody UserRequestDTO user) {
        UserResponseDTO savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> getAllUsers = userService.getAllUsers();
        return ResponseEntity.ok(getAllUsers);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO getUserById = userService.getUserById(id);
        return ResponseEntity.ok(getUserById);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO user) {
        UserResponseDTO updateUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String deleteRole = userService.deleteUser(id);
        return ResponseEntity.ok(deleteRole);
    }

}