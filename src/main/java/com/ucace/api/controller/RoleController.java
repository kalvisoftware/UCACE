package com.ucace.api.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ucace.api.entity.Role;
import com.ucace.api.service.RoleService;
import java.util.*;

@RestController
@RequestMapping("/api/roles")

public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        Role savedRole = roleService.saveRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> getAllRoles = roleService.getAllRoles();
        return ResponseEntity.ok(getAllRoles);
    }

    @GetMapping("{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Role getRoleById = roleService.getRoleById(id);
        return ResponseEntity.ok(getRoleById);
    }

    @PutMapping("{id}")
    public ResponseEntity<Role> updateRole(@RequestBody Role role, @PathVariable Long id) {
        Role updateRole = roleService.updateRole(id, role);
        return ResponseEntity.ok(updateRole);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        String deleteRole = roleService.deleteRole(id);
        return ResponseEntity.ok(deleteRole);
    }
}
