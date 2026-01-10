package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.ActivityRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.entity.Activity;
import com.example.inspire_environment.entity.Attendance;
import com.example.inspire_environment.entity.Staff;
import com.example.inspire_environment.entity.Student;
import com.example.inspire_environment.enums.AttendanceStatus;
import com.example.inspire_environment.mapper.ActivityMapper;
import com.example.inspire_environment.repository.ActivityRepository;
import com.example.inspire_environment.repository.AttendanceRepository;
import com.example.inspire_environment.repository.StaffRepository;
import com.example.inspire_environment.repository.StudentRepository;
import com.example.inspire_environment.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final StudentRepository studentRepository;
    private final StaffRepository staffRepository;
    private final AttendanceRepository attendanceRepository;
    private final ActivityMapper activityMapper;

    @Override
    public ActivityResponseDTO createActivity(ActivityRequestDTO dto) {
        Activity activity = activityMapper.toEntity(dto);
        return activityMapper.toResponseDTO(activityRepository.save(activity));
    }

    @Override
    public ActivityResponseDTO updateActivity(Long id, ActivityRequestDTO dto) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));

        if (dto.getTitle() != null) {
            activity.setTitle(dto.getTitle());
        }
        if (dto.getType() != null) {
            activity.setType(dto.getType());
        }
        if (dto.getStartDate() != null) {
            activity.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            activity.setEndDate(dto.getEndDate());
        }
        if (dto.getManagedBy() != null) {
            activity.setManagedBy(dto.getManagedBy());
        }

        return activityMapper.toResponseDTO(activityRepository.save(activity));
    }

    @Override
    public List<ActivityResponseDTO> getAllActivities() {
        return activityRepository.findAll()
                .stream()
                .map(activityMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<ActivityResponseDTO> getActivitiesByStudent(Long studentId) {
        return activityRepository.findByStudents_Id(studentId)
                .stream()
                .map(activityMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<ActivityResponseDTO> getActivitiesManagedBy(String managedBy) {
        return activityRepository.findByManagedBy(managedBy)
                .stream()
                .map(activityMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<ActivityResponseDTO> getActivitiesByDepartment(Long departmentId) {

        List<Student> students = studentRepository.findByDepartmentId(departmentId);

        // Get all activities that have at least one student from this department
        return students.stream()
                .flatMap(student -> activityRepository.findByStudents_Id(student.getId()).stream())
                .distinct()
                .map(activityMapper::toResponseDTO)
                .toList();
    }


    @Override
    public List<Activity> findByTitleContainingIgnoreCase(String title) {
        return activityRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public void assignStudentToActivity(Long activityId, Long studentId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + activityId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        if (attendanceRepository.findByStudentIdAndActivityId(studentId, activityId).isEmpty()) {
            Attendance attendance = new Attendance();
            attendance.setActivity(activity);
            attendance.setStudent(student);
            attendance.setStatus(AttendanceStatus.PENDING);
            attendanceRepository.save(attendance);
        }
    }

    @Override
    public void assignStaffToActivity(Long activityId, Long staffId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + activityId));
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));

        activity.setManagedBy(staff);
        activityRepository.save(activity);
    }

    @Override
    public void assignAllStudentsByDepartmentToActivity(Long activityId, Long departmentId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + activityId));

        // Get all students from the department
        List<Student> students = studentRepository.findByDepartmentId(departmentId);

        for (Student student : students) {
            if (attendanceRepository.findByStudentIdAndActivityId(student.getId(), activityId).isEmpty()) {
                Attendance attendance = new Attendance();
                attendance.setActivity(activity);
                attendance.setStudent(student);
                attendance.setStatus(AttendanceStatus.PENDING);
                attendanceRepository.save(attendance);
            }
        }
    }

    @Override
    public void removeStudentFromActivity(Long activityId, Long studentId) {

        activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + activityId));
        studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        attendanceRepository.findByStudentIdAndActivityId(studentId, activityId)
                .ifPresent(attendanceRepository::delete);
    }

    @Override
    public void deleteActivity(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));
        activityRepository.delete(activity);
    }

    @Override
    public List<ActivityResponseDTO> getActivitiesThatWeekByStudent(Long studentId) {

        LocalDateTime[] weekBounds = getStartAndEndOfWeek();
        LocalDateTime startOfWeek = weekBounds[0];
        LocalDateTime endOfWeek = weekBounds[1];

        List<Activity> studentActivities = activityRepository.findByStudents_Id(studentId);

        return weekActivities(studentActivities, startOfWeek, endOfWeek);
    }

    @Override
    public List<ActivityResponseDTO> getActivitiesThatWeekByDepartment(Long departmentId) {

        LocalDateTime[] weekBounds = getStartAndEndOfWeek();
        LocalDateTime startOfWeek = weekBounds[0];
        LocalDateTime endOfWeek = weekBounds[1];

        List<Student> students = studentRepository.findByDepartmentId(departmentId);

        List<Activity> departmentActivities = students.stream()
                .flatMap(student -> activityRepository.findByStudents_Id(student.getId()).stream())
                .distinct()
                .toList();

        return weekActivities(departmentActivities, startOfWeek, endOfWeek);
    }
    //==============================================================================================================================================

    private LocalDateTime[] getStartAndEndOfWeek() {

        LocalDateTime now = LocalDateTime.now();
        DayOfWeek currentDay = now.getDayOfWeek();
        LocalDateTime startOfWeek = now.minusDays(currentDay.getValue() - 1).toLocalDate().atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).toLocalDate().atTime(23, 59, 59);

        return new LocalDateTime[]{startOfWeek, endOfWeek};
    }

    private List<ActivityResponseDTO> weekActivities(List<Activity> activities, LocalDateTime startOfWeek, LocalDateTime endOfWeek) {
        return activities.stream()
                .filter(activity -> {
                    LocalDateTime activityStart = activity.getStartDate();
                    LocalDateTime activityEnd = activity.getEndDate();
                    return (activityStart != null && activityEnd != null) &&
                           ((activityStart.isAfter(startOfWeek) || activityStart.isEqual(startOfWeek)) &&
                            (activityStart.isBefore(endOfWeek) || activityStart.isEqual(endOfWeek)));
                })
                .map(activityMapper::toResponseDTO)
                .toList();
    }
}
