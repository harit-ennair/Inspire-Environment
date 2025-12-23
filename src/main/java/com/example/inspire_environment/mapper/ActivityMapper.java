package com.example.inspire_environment.mapper;
import com.example.inspire_environment.dto.request.ActivityRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.entity.Activity;
import com.example.inspire_environment.enums.ActivityType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
@Mapper(componentModel = "spring")
public interface ActivityMapper {
    @Mapping(source = "type", target = "type", qualifiedByName = "activityTypeToString")
    @Mapping(target = "tasks", ignore = true)
    ActivityResponseDTO toResponseDTO(Activity activity);
    @Mapping(source = "type", target = "type", qualifiedByName = "stringToActivityType")
    @Mapping(target = "id", ignore = true)
    Activity toEntity(ActivityRequestDTO dto);
    @Mapping(source = "type", target = "type", qualifiedByName = "stringToActivityType")
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ActivityRequestDTO dto, @MappingTarget Activity activity);
    @Named("activityTypeToString")
    default String activityTypeToString(ActivityType type) {
        return type != null ? type.name() : null;
    }
    @Named("stringToActivityType")
    default ActivityType stringToActivityType(String type) {
        return type != null ? ActivityType.valueOf(type) : null;
    }
}