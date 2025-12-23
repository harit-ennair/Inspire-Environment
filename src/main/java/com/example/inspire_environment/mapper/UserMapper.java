package com.example.inspire_environment.mapper;
import com.example.inspire_environment.dto.response.UserResponseDTO;
import com.example.inspire_environment.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role.name", target = "roleName")
    @Mapping(source = "department.name", target = "departmentName")
    UserResponseDTO toResponseDTO(User user);
}