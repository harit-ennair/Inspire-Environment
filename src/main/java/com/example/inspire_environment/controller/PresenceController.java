package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.request.PresenceRequestDTO;
import com.example.inspire_environment.dto.response.PresenceResponseDTO;
import com.example.inspire_environment.enums.PresenceStatus;
import com.example.inspire_environment.service.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presences")
public class PresenceController {

    @Autowired
    private PresenceService presenceService;

    @PostMapping("/check-in/{studentId}")
    public ResponseEntity<PresenceResponseDTO> checkIn(@PathVariable Long studentId) {
        PresenceResponseDTO response = presenceService.checkIn(studentId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-out/{studentId}")
    public ResponseEntity<PresenceResponseDTO> checkOut(@PathVariable Long studentId) {
        PresenceResponseDTO response = presenceService.checkOut(studentId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PresenceResponseDTO> createPresence(@RequestBody PresenceRequestDTO dto) {
        PresenceResponseDTO response = presenceService.createPresence(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PresenceResponseDTO> updatePresence(
            @PathVariable Long id,
            @RequestBody PresenceRequestDTO dto) {
        PresenceResponseDTO response = presenceService.updatePresence(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PresenceResponseDTO>> getAllPresences() {
        List<PresenceResponseDTO> presences = presenceService.getAllPresences();
        return ResponseEntity.ok(presences);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<PresenceResponseDTO>> getPresencesByStudent(@PathVariable Long studentId) {
        List<PresenceResponseDTO> presences = presenceService.getPresencesByStudent(studentId);
        return ResponseEntity.ok(presences);
    }

    //++++++++++++++++++++++++++++++++++++
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PresenceResponseDTO>> getPresencesByStatus(@PathVariable PresenceStatus status) {
        List<PresenceResponseDTO> presences = presenceService.getPresencesByStatus(status);
        return ResponseEntity.ok(presences);
    }

    @GetMapping("/active")
    public ResponseEntity<List<PresenceResponseDTO>> getActivePresences() {
        List<PresenceResponseDTO> presences = presenceService.getActivePresences();
        return ResponseEntity.ok(presences);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePresence(@PathVariable Long id) {
        presenceService.deletePresence(id);
        return ResponseEntity.noContent().build();
    }
}

