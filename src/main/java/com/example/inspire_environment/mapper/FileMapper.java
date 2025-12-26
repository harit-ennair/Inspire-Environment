package com.example.inspire_environment.mapper;

import com.example.inspire_environment.dto.request.FileRequestDTO;
import com.example.inspire_environment.dto.response.FileResponseDTO;
import com.example.inspire_environment.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mapping(target = "submission", ignore = true)
    FileResponseDTO toResponseDTO(File file);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "submission", ignore = true)
    File toEntity(FileRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "submission", ignore = true)
    void updateEntityFromDTO(FileRequestDTO dto, @MappingTarget File file);
}
