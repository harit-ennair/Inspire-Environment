package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.request.FileRequestDTO;
import com.example.inspire_environment.dto.response.FileResponseDTO;

import java.util.List;

public interface FileService {

    FileResponseDTO createFile(FileRequestDTO fileRequestDTO);

    FileResponseDTO getFileById(Long id);

    List<FileResponseDTO> getAllFiles();

    List<FileResponseDTO> getFilesBySubmissionId(Long submissionId);

    FileResponseDTO updateFile(Long id, FileRequestDTO fileRequestDTO);

    void deleteFile(Long id);
}
