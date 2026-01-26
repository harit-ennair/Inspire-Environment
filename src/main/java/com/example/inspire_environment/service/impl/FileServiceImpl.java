package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.FileRequestDTO;
import com.example.inspire_environment.dto.response.FileResponseDTO;
import com.example.inspire_environment.entity.File;
import com.example.inspire_environment.entity.Submission;
import com.example.inspire_environment.exception.ResourceNotFoundException;
import com.example.inspire_environment.mapper.FileMapper;
import com.example.inspire_environment.repository.FileRepository;
import com.example.inspire_environment.repository.SubmissionRepository;
import com.example.inspire_environment.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final SubmissionRepository submissionRepository;
    private final FileMapper fileMapper;

    @Override
    public FileResponseDTO createFile(FileRequestDTO fileRequestDTO) {
        File file = fileMapper.toEntity(fileRequestDTO);

        if (fileRequestDTO.getSubmissionId() != null) {
            Submission submission = submissionRepository.findById(fileRequestDTO.getSubmissionId())
                    .orElseThrow(() -> new RuntimeException("Submission not found"));
            file.setSubmission(submission);
        }

        File savedFile = fileRepository.save(file);
        return fileMapper.toResponseDTO(savedFile);
    }

    @Override
    @Transactional(readOnly = true)
    public FileResponseDTO getFileById(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
        return fileMapper.toResponseDTO(file);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileResponseDTO> getAllFiles() {
        return fileRepository.findAll()
                .stream()
                .map(fileMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileResponseDTO> getFilesBySubmissionId(Long submissionId) {
        return fileRepository.findBySubmissionId(submissionId)
                .stream()
                .map(fileMapper::toResponseDTO)
                .toList();
    }

    @Override
    public FileResponseDTO updateFile(Long id, FileRequestDTO fileRequestDTO) {
        File existingFile = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File", "id", id));

        fileMapper.updateEntityFromDTO(fileRequestDTO, existingFile);

        if (fileRequestDTO.getSubmissionId() != null) {
            Submission submission = submissionRepository.findById(fileRequestDTO.getSubmissionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Submission", "id", fileRequestDTO.getSubmissionId()));
            existingFile.setSubmission(submission);
        }

        File savedFile = fileRepository.save(existingFile);
        return fileMapper.toResponseDTO(savedFile);
    }

    @Override
    public void deleteFile(Long id) {
        if (!fileRepository.existsById(id)) {
            throw new ResourceNotFoundException("File", "id", id);
        }
        fileRepository.deleteById(id);
    }
}
