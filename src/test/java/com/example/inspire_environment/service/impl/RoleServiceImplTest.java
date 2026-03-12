package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.RoleRequestDTO;
import com.example.inspire_environment.dto.response.RoleResponseDTO;
import com.example.inspire_environment.entity.Role;
import com.example.inspire_environment.exception.ConflictException;
import com.example.inspire_environment.exception.ResourceNotFoundException;
import com.example.inspire_environment.mapper.RoleMapper;
import com.example.inspire_environment.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;
    private RoleRequestDTO roleRequestDTO;
    private RoleResponseDTO roleResponseDTO;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        role.setDescription("Standard User");

        roleRequestDTO = new RoleRequestDTO();
        roleRequestDTO.setName("ROLE_USER");
        roleRequestDTO.setDescription("Standard User");

        roleResponseDTO = new RoleResponseDTO();
        roleResponseDTO.setId(1L);
        roleResponseDTO.setName("ROLE_USER");
        roleResponseDTO.setDescription("Standard User");
    }

    @Test
    void getRoleById_ShouldReturnRoleResponseDTO_WhenRoleExists() {
        // Arrange
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleMapper.toResponseDTO(role)).thenReturn(roleResponseDTO);

        // Act
        RoleResponseDTO result = roleService.getRoleById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("ROLE_USER", result.getName());
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    void createRole_ShouldReturnRoleRequestDTO_WhenSavedSuccessfully() {
        // Arrange
        when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(roleMapper.toEntity(any(RoleRequestDTO.class))).thenReturn(role);
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        // Act
        RoleRequestDTO result = roleService.createRole(roleRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("ROLE_USER", result.getName());
        assertEquals("Standard User", result.getDescription());
        verify(roleRepository, times(1)).findByName("ROLE_USER");
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void createRole_ShouldThrowConflictException_WhenRoleNameAlreadyExists() {
        // Arrange
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class, () -> {
            roleService.createRole(roleRequestDTO);
        });

        assertTrue(exception.getMessage().contains("already exists"));
        verify(roleRepository, times(1)).findByName("ROLE_USER");
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void getRoleById_ShouldThrowResourceNotFoundException_WhenRoleDoesNotExist() {
        // Arrange
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            roleService.getRoleById(1L);
        });

        assertTrue(exception.getMessage().contains("Role not found"));
        verify(roleRepository, times(1)).findById(1L);
        verify(roleMapper, never()).toResponseDTO(any(Role.class));
    }

    @Test
    void getAllRoles_ShouldReturnListOfRoleResponseDTO() {
        // Arrange
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("ROLE_ADMIN");
        role2.setDescription("Administrator");

        RoleResponseDTO roleResponseDTO2 = new RoleResponseDTO();
        roleResponseDTO2.setId(2L);
        roleResponseDTO2.setName("ROLE_ADMIN");
        roleResponseDTO2.setDescription("Administrator");

        List<Role> roles = Arrays.asList(role, role2);

        when(roleRepository.findAll()).thenReturn(roles);
        when(roleMapper.toResponseDTO(role)).thenReturn(roleResponseDTO);
        when(roleMapper.toResponseDTO(role2)).thenReturn(roleResponseDTO2);

        // Act
        List<RoleResponseDTO> result = roleService.getAllRoles();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ROLE_USER", result.get(0).getName());
        assertEquals("ROLE_ADMIN", result.get(1).getName());
        verify(roleRepository, times(1)).findAll();
        verify(roleMapper, times(2)).toResponseDTO(any(Role.class));
    }

    @Test
    void getAllRoles_ShouldReturnEmptyList_WhenNoRolesExist() {
        // Arrange
        when(roleRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<RoleResponseDTO> result = roleService.getAllRoles();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void updateRole_ShouldReturnUpdatedRoleRequestDTO_WhenSuccessful() {
        // Arrange
        RoleRequestDTO updateRequest = new RoleRequestDTO();
        updateRequest.setName("ROLE_USER_UPDATED");
        updateRequest.setDescription("Updated Standard User");

        Role updatedRole = new Role();
        updatedRole.setId(1L);
        updatedRole.setName("ROLE_USER_UPDATED");
        updatedRole.setDescription("Updated Standard User");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleRepository.findByName("ROLE_USER_UPDATED")).thenReturn(Optional.empty());
        doNothing().when(roleMapper).updateEntityFromDTO(any(RoleRequestDTO.class), any(Role.class));
        when(roleRepository.save(any(Role.class))).thenReturn(updatedRole);

        // Act
        RoleRequestDTO result = roleService.updateRole(1L, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals("ROLE_USER_UPDATED", result.getName());
        assertEquals("Updated Standard User", result.getDescription());
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).findByName("ROLE_USER_UPDATED");
        verify(roleMapper, times(1)).updateEntityFromDTO(updateRequest, role);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void updateRole_ShouldThrowResourceNotFoundException_WhenRoleDoesNotExist() {
        // Arrange
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            roleService.updateRole(1L, roleRequestDTO);
        });

        assertTrue(exception.getMessage().contains("Role not found"));
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void updateRole_ShouldThrowConflictException_WhenUpdatingToExistingRoleName() {
        // Arrange
        Role anotherRole = new Role();
        anotherRole.setId(2L);
        anotherRole.setName("ROLE_ADMIN");

        RoleRequestDTO updateRequest = new RoleRequestDTO();
        updateRequest.setName("ROLE_ADMIN");
        updateRequest.setDescription("Admin Role");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(anotherRole));

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class, () -> {
            roleService.updateRole(1L, updateRequest);
        });

        assertTrue(exception.getMessage().contains("already exists"));
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).findByName("ROLE_ADMIN");
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void updateRole_ShouldSucceed_WhenUpdatingRoleWithSameName() {
        // Arrange
        RoleRequestDTO updateRequest = new RoleRequestDTO();
        updateRequest.setName("ROLE_USER"); // Same name
        updateRequest.setDescription("Updated Description");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role)); // Same role
        doNothing().when(roleMapper).updateEntityFromDTO(any(RoleRequestDTO.class), any(Role.class));
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        // Act
        RoleRequestDTO result = roleService.updateRole(1L, updateRequest);

        // Assert
        assertNotNull(result);
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).findByName("ROLE_USER");
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void deleteRole_ShouldDeleteSuccessfully_WhenRoleExists() {
        // Arrange
        when(roleRepository.existsById(1L)).thenReturn(true);
        doNothing().when(roleRepository).deleteById(1L);

        // Act
        roleService.deleteRole(1L);

        // Assert
        verify(roleRepository, times(1)).existsById(1L);
        verify(roleRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteRole_ShouldThrowResourceNotFoundException_WhenRoleDoesNotExist() {
        // Arrange
        when(roleRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            roleService.deleteRole(1L);
        });

        assertTrue(exception.getMessage().contains("Role not found"));
        verify(roleRepository, times(1)).existsById(1L);
        verify(roleRepository, never()).deleteById(anyLong());
    }
}
