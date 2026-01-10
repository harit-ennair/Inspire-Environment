package com.example.inspire_environment.mapper;

import com.example.inspire_environment.dto.request.PresenceRequestDTO;
import com.example.inspire_environment.dto.response.PresenceResponseDTO;
import com.example.inspire_environment.entity.Presence;
import com.example.inspire_environment.enums.PresenceStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface PresenceMapper {

    @Mapping(source = "status", target = "status", qualifiedByName = "presenceStatusToString")
    @Mapping(target = "student", ignore = true) // Avoid circular reference
    PresenceResponseDTO toResponseDTO(Presence presence);

    @Mapping(source = "status", target = "status", qualifiedByName = "stringToPresenceStatus")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    Presence toEntity(PresenceRequestDTO dto);

    @Mapping(source = "status", target = "status", qualifiedByName = "stringToPresenceStatus")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    void updateEntityFromDTO(PresenceRequestDTO dto, @MappingTarget Presence presence);

    @Named("presenceStatusToString")
    default String presenceStatusToString(PresenceStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("stringToPresenceStatus")
    default PresenceStatus stringToPresenceStatus(String status) {
        return status != null ? PresenceStatus.valueOf(status) : null;
    }
}

