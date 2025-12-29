package com.example.inspire_environment.mapper;
import com.example.inspire_environment.dto.request.StudentRequestDTO;
import com.example.inspire_environment.dto.response.StudentResponseDTO;
import com.example.inspire_environment.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface StudentMapper {
    @Mapping(source = "studentCode", target = "studentCode")
    @Mapping(source = "id", target = "user.id")
    @Mapping(source = "firstName", target = "user.firstName")
    @Mapping(source = "lastName", target = "user.lastName")
    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "role.name", target = "user.roleName")
    @Mapping(source = "department.name", target = "user.departmentName")
    StudentResponseDTO toResponseDTO(Student student);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.password", target = "password")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "attendances", ignore = true)
    @Mapping(target = "submissions", ignore = true)
    Student toEntity(StudentRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.password", target = "password")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "attendances", ignore = true)
    @Mapping(target = "submissions", ignore = true)
    void updateEntityFromDTO(StudentRequestDTO dto, @MappingTarget Student student);
}