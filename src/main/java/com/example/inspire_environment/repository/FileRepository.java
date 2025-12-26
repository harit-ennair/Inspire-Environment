package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findBySubmissionId(Long submissionId);
}
