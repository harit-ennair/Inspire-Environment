package com.example.inspire_environment.mapper;
import com.example.inspire_environment.dto.response.DepartmentResponseDTO;
import com.example.inspire_environment.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface DepartmentMapper {
    @Mapping(target = "users", ignore = true)
    DepartmentResponseDTO toResponseDTO(Department department);
}