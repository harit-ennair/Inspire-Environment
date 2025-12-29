package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.Student;
import com.example.inspire_environment.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

//    @Query("SELECT s FROM Submission s JOIN Student st ON s MEMBER OF st.submissions WHERE st.id = :studentId")
//    List<Submission> findByStudentId(Long studentId);
//
    List<Submission> findByStudentId(Long studentId);

    List<Submission> findByTaskId(Long taskId);

//    @Query("SELECT s FROM Submission s JOIN Student st ON s MEMBER OF st.submissions WHERE st.id = :studentId AND s.task.id = :taskId")
//    Optional<Submission> findByStudentIdAndTaskId(Long studentId, Long taskId);

    Optional<Submission> findByStudentIdAndTaskId(Long studentId, Long taskId);

//    @Query("SELECT s FROM Submission s WHERE s.submittedAt BETWEEN :start AND :end")
//    List<Submission> findBySubmittedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Submission> findBySubmittedAtBetween(LocalDateTime start, LocalDateTime end);

//    @Query("SELECT s FROM Submission s JOIN s.task t WHERE t.deadline < s.submittedAt")
//    List<Submission> findLateSubmissions();

    List<Submission> findByStudentAndSubmittedAtAfter(Student student, LocalDateTime deadline);

    @Query("SELECT s FROM Submission s JOIN s.task t JOIN t.activity a WHERE a.managedBy.id = :staffId")
    List<Submission> findSubmissionsByStaffId(Long staffId);
}
