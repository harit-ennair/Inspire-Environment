package com.example.inspire_environment.mapper;
import com.example.inspire_environment.dto.request.ActivityRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.entity.Activity;
import com.example.inspire_environment.enums.ActivityType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface ActivityMapper {
    @Mapping(source = "type", target = "type", qualifiedByName = "activityTypeToString")
    @Mapping(source = "managedBy.firstName", target = "managedBy")
    @Mapping(source = "startTime", target = "startDate")
    @Mapping(source = "endTime", target = "endDate")
    @Mapping(target = "tasks", source = "tasks")
    ActivityResponseDTO toResponseDTO(Activity activity);

    @Mapping(source = "managedBy.id", target = "managedBy")
    ActivityRequestDTO toRequestDTO(Activity activity);

    @Mapping(source = "type", target = "type", qualifiedByName = "stringToActivityType")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "managedBy", target = "managedBy.id")
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "attendances", ignore = true)
    Activity toEntity(ActivityRequestDTO dto);

    @Mapping(source = "type", target = "type", qualifiedByName = "stringToActivityType")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "managedBy", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "attendances", ignore = true)
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