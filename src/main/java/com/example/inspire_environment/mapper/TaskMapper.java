package com.example.inspire_environment.mapper;
import com.example.inspire_environment.dto.request.TaskRequestDTO;
import com.example.inspire_environment.dto.response.TaskResponseDTO;
import com.example.inspire_environment.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "activity", ignore = true)
    @Mapping(target = "submissions", ignore = true)
    TaskResponseDTO toResponseDTO(Task task);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activity", ignore = true)
    Task toEntity(TaskRequestDTO dto);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activity", ignore = true)
    void updateEntityFromDTO(TaskRequestDTO dto, @MappingTarget Task task);
}