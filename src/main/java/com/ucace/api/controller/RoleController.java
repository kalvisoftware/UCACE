package com.ucace.api.controller;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ucace.api.dto.RoleRequestDTO;
import com.ucace.api.dto.RoleResponseDTO;
import com.ucace.api.response.ApiResponseDTO;
import com.ucace.api.service.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Role Management", description = "APIs for managing roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Save Role", description = "Create a new role")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Role created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<ApiResponseDTO<RoleResponseDTO>> saveRole(@Valid @RequestBody RoleRequestDTO role) {
        RoleResponseDTO savedRole = roleService.saveRole(role);
        ApiResponseDTO<RoleResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Role created Successfully",
                savedRole,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Get All Roles", description = "Retrieve a list of all roles")
    public ResponseEntity<ApiResponseDTO<List<RoleResponseDTO>>> getAllRoles() {
        List<RoleResponseDTO> getAllRoles = roleService.getAllRoles();
        ApiResponseDTO<List<RoleResponseDTO>> response = new ApiResponseDTO<>(
                true,
                "All Role Retrived Successfully",
                getAllRoles,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")

    @Operation(summary = "Get Role by ID", description = "Retrieve a role by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role found"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<ApiResponseDTO<RoleResponseDTO>> getRoleById(@PathVariable Long id) {
        RoleResponseDTO getRoleById = roleService.getRoleById(id);
        ApiResponseDTO<RoleResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Role by id retrived Successfully",
                getRoleById,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update Role", description = "Update an existing role by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role updated successfully"),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<ApiResponseDTO<RoleResponseDTO>> updateRole(@Valid @RequestBody RoleRequestDTO role,
            @PathVariable Long id) {
        RoleResponseDTO updateRole = roleService.updateRole(id, role);
        ApiResponseDTO<RoleResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Role updated Successfully",
                updateRole,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete Role", description = "Delete a role by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<ApiResponseDTO<String>> deleteRole(@PathVariable Long id) {
        String deleteRole = roleService.deleteRole(id);
        ApiResponseDTO<String> response = new ApiResponseDTO<>(
                true,
                "Role Deleted Successfully",
                deleteRole,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
