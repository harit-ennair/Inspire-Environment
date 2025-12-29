package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByDepartmentId(Long departmentId);
}
