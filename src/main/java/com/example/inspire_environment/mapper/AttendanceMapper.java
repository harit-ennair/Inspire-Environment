package com.example.inspire_environment.mapper;
import com.example.inspire_environment.dto.request.AttendanceRequestDTO;
import com.example.inspire_environment.dto.response.AttendanceResponseDTO;
import com.example.inspire_environment.entity.Attendance;
import com.example.inspire_environment.enums.AttendanceStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
@Mapper(componentModel = "spring")
public interface AttendanceMapper {
    @Mapping(source = "status", target = "status", qualifiedByName = "attendanceStatusToString")
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "activity", ignore = true)
    AttendanceResponseDTO toResponseDTO(Attendance attendance);
    @Mapping(source = "status", target = "status", qualifiedByName = "stringToAttendanceStatus")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "checkInTime", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "activity", ignore = true)
    Attendance toEntity(AttendanceRequestDTO dto);
    @Mapping(source = "status", target = "status", qualifiedByName = "stringToAttendanceStatus")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "checkInTime", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "activity", ignore = true)
    void updateEntityFromDTO(AttendanceRequestDTO dto, @MappingTarget Attendance attendance);
    @Named("attendanceStatusToString")
    default String attendanceStatusToString(AttendanceStatus status) {
        return status != null ? status.name() : null;
    }
    @Named("stringToAttendanceStatus")
    default AttendanceStatus stringToAttendanceStatus(String status) {
        return status != null ? AttendanceStatus.valueOf(status) : null;
    }
}