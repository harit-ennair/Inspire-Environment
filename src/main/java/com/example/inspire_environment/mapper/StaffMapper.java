package com.example.inspire_environment.mapper;
import com.example.inspire_environment.dto.request.StaffRequestDTO;
import com.example.inspire_environment.dto.response.StaffResponseDTO;
import com.example.inspire_environment.entity.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface StaffMapper {
    @Mapping(source = "position", target = "position")
    @Mapping(source = "id", target = "user.id")
    @Mapping(source = "firstName", target = "user.firstName")
    @Mapping(source = "lastName", target = "user.lastName")
    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "role.name", target = "user.roleName")
    @Mapping(source = "department.name", target = "user.departmentName")
    StaffResponseDTO toResponseDTO(Staff staff);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.password", target = "password")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "activities", ignore = true)
    Staff toEntity(StaffRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.password", target = "password")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "activities", ignore = true)
    void updateEntityFromDTO(StaffRequestDTO dto, @MappingTarget Staff staff);
}