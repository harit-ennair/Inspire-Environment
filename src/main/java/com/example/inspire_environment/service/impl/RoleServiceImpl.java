package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.RoleRequestDTO;
import com.example.inspire_environment.dto.response.RoleResponseDTO;
import com.example.inspire_environment.entity.Role;
import com.example.inspire_environment.mapper.RoleMapper;
import com.example.inspire_environment.repository.RoleRepository;
import com.example.inspire_environment.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleRequestDTO createRole(RoleRequestDTO roleRequestDTO) {
        // Check if role with the same name already exists
        roleRepository.findByName(roleRequestDTO.getName())
                .ifPresent(role -> {
                    throw new IllegalArgumentException("Role with name '" + roleRequestDTO.getName() + "' already exists");
                });

        Role role = roleMapper.toEntity(roleRequestDTO);
        Role savedRole = roleRepository.save(role);

        RoleRequestDTO responseDTO = new RoleRequestDTO();
        responseDTO.setName(savedRole.getName());
        responseDTO.setDescription(savedRole.getDescription());
        return responseDTO;
    }

    @Override
    public RoleRequestDTO updateRole(Long roleId, RoleRequestDTO roleRequestDTO) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId));

        // Check if updating to a name that already exists (excluding current role)
        roleRepository.findByName(roleRequestDTO.getName())
                .ifPresent(role -> {
                    if (!role.getId().equals(roleId)) {
                        throw new IllegalArgumentException("Role with name '" + roleRequestDTO.getName() + "' already exists");
                    }
                });

        roleMapper.updateEntityFromDTO(roleRequestDTO, existingRole);
        Role updatedRole = roleRepository.save(existingRole);

        RoleRequestDTO responseDTO = new RoleRequestDTO();
        responseDTO.setName(updatedRole.getName());
        responseDTO.setDescription(updatedRole.getDescription());
        return responseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDTO getRoleById(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId));
        return roleMapper.toResponseDTO(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRole(Long roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new IllegalArgumentException("Role not found with id: " + roleId);
        }
        roleRepository.deleteById(roleId);
    }
}
