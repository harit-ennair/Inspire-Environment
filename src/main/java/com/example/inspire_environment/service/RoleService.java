package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.request.RoleRequestDTO;
import com.example.inspire_environment.dto.response.RoleResponseDTO;

import java.util.List;

public interface RoleService {

    RoleRequestDTO createRole(RoleRequestDTO roleRequestDTO);

    RoleRequestDTO updateRole(Long roleId, RoleRequestDTO roleRequestDTO);

    RoleResponseDTO getRoleById(Long roleId);

    List<RoleResponseDTO> getAllRoles();

    void deleteRole(Long roleId);
}
