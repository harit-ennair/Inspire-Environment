package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.PresenceRequestDTO;
import com.example.inspire_environment.dto.response.PresenceResponseDTO;
import com.example.inspire_environment.entity.Presence;
import com.example.inspire_environment.entity.Student;
import com.example.inspire_environment.enums.PresenceStatus;
import com.example.inspire_environment.mapper.PresenceMapper;
import com.example.inspire_environment.repository.PresenceRepository;
import com.example.inspire_environment.repository.StudentRepository;
import com.example.inspire_environment.service.PresenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PresenceServiceImpl implements PresenceService {

    private final PresenceRepository presenceRepository;
    private final StudentRepository studentRepository;
    private final PresenceMapper presenceMapper;

    @Override
    public PresenceResponseDTO checkIn(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        // Check if student already has an active presence (checked in but not checked out)
        List<Presence> activePresences = presenceRepository.findActivePresences();
        boolean alreadyCheckedIn = activePresences.stream()
                .anyMatch(p -> p.getStudent().getId().equals(studentId));

        if (alreadyCheckedIn) {
            throw new RuntimeException("Student is already checked in");
        }

        Presence presence = new Presence();
        presence.setStudent(student);
        presence.setCheckInTime(LocalDateTime.now());
        presence.setStatus(PresenceStatus.PRESENT);

        Presence savedPresence = presenceRepository.save(presence);
        return presenceMapper.toResponseDTO(savedPresence);
    }

    @Override
    public PresenceResponseDTO checkOut(Long studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        // Find active presence for this student
        List<Presence> activePresences = presenceRepository.findActivePresences();
        Presence activePresence = activePresences.stream()
                .filter(p -> p.getStudent().getId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active check-in found for student"));

        activePresence.setCheckOutTime(LocalDateTime.now());
//        activePresence.setStatus(PresenceStatus.LEFT);

        Presence savedPresence = presenceRepository.save(activePresence);
        return presenceMapper.toResponseDTO(savedPresence);
    }

    @Override
    public PresenceResponseDTO createPresence(PresenceRequestDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));

        Presence presence = presenceMapper.toEntity(dto);
        presence.setStudent(student);

        if (presence.getCheckInTime() == null) {
            presence.setCheckInTime(LocalDateTime.now());
        }

        if (presence.getStatus() == null) {
            presence.setStatus(PresenceStatus.PRESENT);
        }

        Presence savedPresence = presenceRepository.save(presence);
        return presenceMapper.toResponseDTO(savedPresence);
    }

    @Override
    public PresenceResponseDTO updatePresence(Long id, PresenceRequestDTO dto) {
        Presence presence = presenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presence not found with id: " + id));

        if (dto.getCheckInTime() != null) {
            presence.setCheckInTime(dto.getCheckInTime());
        }
        if (dto.getCheckOutTime() != null) {
            presence.setCheckOutTime(dto.getCheckOutTime());
        }
        if (dto.getStatus() != null) {
            presence.setStatus(dto.getStatus());
        }
        if (dto.getStudentId() != null && !dto.getStudentId().equals(presence.getStudent().getId())) {
            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
            presence.setStudent(student);
        }

        Presence savedPresence = presenceRepository.save(presence);
        return presenceMapper.toResponseDTO(savedPresence);
    }

    @Override
    public PresenceResponseDTO getPresenceById(Long id) {
        Presence presence = presenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presence not found with id: " + id));
        return presenceMapper.toResponseDTO(presence);
    }

    @Override
    public List<PresenceResponseDTO> getAllPresences() {
        return presenceRepository.findAll()
                .stream()
                .map(presenceMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<PresenceResponseDTO> getPresencesByStudent(Long studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        return presenceRepository.findByStudentId(studentId)
                .stream()
                .map(presenceMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<PresenceResponseDTO> getPresencesByStatus(PresenceStatus status) {
        return presenceRepository.findByStatus(status)
                .stream()
                .map(presenceMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<PresenceResponseDTO> getPresencesByDateRange(LocalDateTime start, LocalDateTime end) {
        return presenceRepository.findByCheckInTimeBetween(start, end)
                .stream()
                .map(presenceMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<PresenceResponseDTO> getPresencesByStudentAndDateRange(Long studentId, LocalDateTime start, LocalDateTime end) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        return presenceRepository.findByStudentIdAndCheckInTimeBetween(studentId, start, end)
                .stream()
                .map(presenceMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<PresenceResponseDTO> getActivePresences() {
        return presenceRepository.findActivePresences()
                .stream()
                .map(presenceMapper::toResponseDTO)
                .toList();
    }

    @Override
    public void deletePresence(Long id) {
        Presence presence = presenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presence not found with id: " + id));
        presenceRepository.delete(presence);
    }
}

