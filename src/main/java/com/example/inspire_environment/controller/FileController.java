package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.request.FileRequestDTO;
import com.example.inspire_environment.dto.response.FileResponseDTO;
import com.example.inspire_environment.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<FileResponseDTO> createFile(@RequestBody FileRequestDTO fileRequestDTO) {
        FileResponseDTO createdFile = fileService.createFile(fileRequestDTO);
        return new ResponseEntity<>(createdFile, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileResponseDTO> getFileById(@PathVariable Long id) {
        FileResponseDTO file = fileService.getFileById(id);
        return ResponseEntity.ok(file);
    }

    @GetMapping
    public ResponseEntity<List<FileResponseDTO>> getAllFiles() {
        List<FileResponseDTO> files = fileService.getAllFiles();
        return ResponseEntity.ok(files);
    }

    @GetMapping("/submission/{submissionId}")
    public ResponseEntity<List<FileResponseDTO>> getFilesBySubmissionId(@PathVariable Long submissionId) {
        List<FileResponseDTO> files = fileService.getFilesBySubmissionId(submissionId);
        return ResponseEntity.ok(files);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FileResponseDTO> updateFile(@PathVariable Long id, @RequestBody FileRequestDTO fileRequestDTO) {
        FileResponseDTO updatedFile = fileService.updateFile(id, fileRequestDTO);
        return ResponseEntity.ok(updatedFile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }
}
