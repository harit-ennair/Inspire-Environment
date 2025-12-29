package com.example.inspire_environment.mapper;
import com.example.inspire_environment.dto.request.RoleRequestDTO;
import com.example.inspire_environment.dto.response.RoleResponseDTO;
import com.example.inspire_environment.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponseDTO toResponseDTO(Role role);

    @Mapping(target = "id", ignore = true)
    Role toEntity(RoleRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(RoleRequestDTO dto, @MappingTarget Role role);
}