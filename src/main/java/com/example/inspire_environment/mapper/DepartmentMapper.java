package com.example.inspire_environment.mapper;
import com.example.inspire_environment.dto.request.DepartmentRequestDTO;
import com.example.inspire_environment.dto.response.DepartmentResponseDTO;
import com.example.inspire_environment.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface DepartmentMapper {
    @Mapping(target = "users", ignore = true)
    DepartmentResponseDTO toResponseDTO(Department department);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Department toEntity(DepartmentRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    void updateEntityFromDTO(DepartmentRequestDTO dto, @MappingTarget Department department);
}