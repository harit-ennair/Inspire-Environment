package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.StudentRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.dto.response.AttendanceResponseDTO;
import com.example.inspire_environment.dto.response.StudentResponseDTO;
import com.example.inspire_environment.entity.Department;
import com.example.inspire_environment.entity.Role;
import com.example.inspire_environment.entity.Student;
import com.example.inspire_environment.mapper.ActivityMapper;
import com.example.inspire_environment.mapper.AttendanceMapper;
import com.example.inspire_environment.mapper.StudentMapper;
import com.example.inspire_environment.repository.ActivityRepository;
import com.example.inspire_environment.repository.AttendanceRepository;
import com.example.inspire_environment.repository.DepartmentRepository;
import com.example.inspire_environment.repository.RoleRepository;
import com.example.inspire_environment.repository.StudentRepository;
import com.example.inspire_environment.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final ActivityRepository activityRepository;
    private final AttendanceRepository attendanceRepository;
    private final StudentMapper studentMapper;
    private final ActivityMapper activityMapper;
    private final AttendanceMapper attendanceMapper;

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return studentMapper.toResponseDTO(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudentsByDepartment(Long departmentId) {
        return studentRepository.findByDepartmentId(departmentId)
                .stream()
                .map(studentMapper::toResponseDTO)
                .toList();
    }

    @Override
    public StudentRequestDTO createStudent(StudentRequestDTO studentDto) {
        // Check if student code already exists
        studentRepository.findByStudentCode(studentDto.getStudentCode())
                .ifPresent(existingStudent -> {
                    throw new IllegalArgumentException("Student with code '" + studentDto.getStudentCode() + "' already exists");
                });

        // Check if email already exists
        studentRepository.findByEmail(studentDto.getUser().getEmail())
                .ifPresent(existingStudent -> {
                    throw new IllegalArgumentException("Student with email '" + studentDto.getUser().getEmail() + "' already exists");
                });

        Student student = studentMapper.toEntity(studentDto);

        // Set role if provided
        if (studentDto.getUser().getRoleId() != null) {
            Role role = roleRepository.findById(studentDto.getUser().getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + studentDto.getUser().getRoleId()));
            student.setRole(role);
        }

        // Set department if provided
        if (studentDto.getUser().getDepartmentId() != null) {
            Department department = departmentRepository.findById(studentDto.getUser().getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with id: " + studentDto.getUser().getDepartmentId()));
            student.setDepartment(department);
        }

        studentRepository.save(student);
        return studentDto;
    }

    @Override
    public StudentRequestDTO updateStudent(Long id, StudentRequestDTO studentDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        // Check if student code already exists (excluding current student)
        studentRepository.findByStudentCode(studentDto.getStudentCode())
                .ifPresent(existingStudent -> {
                    if (!existingStudent.getId().equals(id)) {
                        throw new IllegalArgumentException("Student with code '" + studentDto.getStudentCode() + "' already exists");
                    }
                });

        // Check if email already exists (excluding current student)
        studentRepository.findByEmail(studentDto.getUser().getEmail())
                .ifPresent(existingStudent -> {
                    if (!existingStudent.getId().equals(id)) {
                        throw new IllegalArgumentException("Student with email '" + studentDto.getUser().getEmail() + "' already exists");
                    }
                });

        studentMapper.updateEntityFromDTO(studentDto, student);

        // Update role if provided
        if (studentDto.getUser().getRoleId() != null) {
            Role role = roleRepository.findById(studentDto.getUser().getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + studentDto.getUser().getRoleId()));
            student.setRole(role);
        }

        // Update department if provided
        if (studentDto.getUser().getDepartmentId() != null) {
            Department department = departmentRepository.findById(studentDto.getUser().getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with id: " + studentDto.getUser().getDepartmentId()));
            student.setDepartment(department);
        }

        studentRepository.save(student);
        return studentDto;
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityResponseDTO> getMyActivities(Long studentId) {
        // Verify student exists
        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found with id: " + studentId);
        }

        return activityRepository.findByStudents_Id(studentId)
                .stream()
                .map(activityMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> getMyAttendances(Long studentId) {
        // Verify student exists
        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found with id: " + studentId);
        }

        return attendanceRepository.findByStudentId(studentId)
                .stream()
                .map(attendanceMapper::toResponseDTO)
                .toList();
    }
}
