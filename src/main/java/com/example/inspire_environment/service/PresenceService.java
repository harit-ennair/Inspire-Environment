package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.request.PresenceRequestDTO;
import com.example.inspire_environment.dto.response.PresenceResponseDTO;
import com.example.inspire_environment.enums.PresenceStatus;

import java.util.List;

public interface PresenceService {

    PresenceResponseDTO checkIn(Long studentId);

    PresenceResponseDTO checkOut(Long studentId);

    PresenceResponseDTO createPresence(PresenceRequestDTO dto);

    PresenceResponseDTO updatePresence(Long id, PresenceRequestDTO dto);

    List<PresenceResponseDTO> getAllPresences();

    List<PresenceResponseDTO> getPresencesByStudent(Long studentId);

    List<PresenceResponseDTO> getPresencesByStatus(PresenceStatus status);

    List<PresenceResponseDTO> getActivePresences();

    void deletePresence(Long id);
}
