package com.example.inspire_environment.mapper;
import com.example.inspire_environment.dto.request.SubmissionRequestDTO;
import com.example.inspire_environment.dto.response.SubmissionResponseDTO;
import com.example.inspire_environment.entity.Submission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface SubmissionMapper {
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "files", ignore = true)
    SubmissionResponseDTO toResponseDTO(Submission submission);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    @Mapping(target = "files", ignore = true)
    Submission toEntity(SubmissionRequestDTO dto);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    @Mapping(target = "files", ignore = true)
    void updateEntityFromDTO(SubmissionRequestDTO dto, @MappingTarget Submission submission);
}