package com.example.inspire_environment.mapper;
import com.example.inspire_environment.dto.response.RoleResponseDTO;
import com.example.inspire_environment.entity.Role;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponseDTO toResponseDTO(Role role);
}