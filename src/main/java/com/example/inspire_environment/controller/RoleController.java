package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.request.RoleRequestDTO;
import com.example.inspire_environment.dto.response.RoleResponseDTO;
import com.example.inspire_environment.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleRequestDTO> createRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        RoleRequestDTO response = roleService.createRole(roleRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleRequestDTO> updateRole(
            @PathVariable Long roleId,
            @RequestBody RoleRequestDTO roleRequestDTO) {
        RoleRequestDTO response = roleService.updateRole(roleId, roleRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long roleId) {
        RoleResponseDTO response = roleService.getRoleById(roleId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<RoleResponseDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }
}

