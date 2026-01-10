package com.example.inspire_environment.entity;

import com.example.inspire_environment.enums.PresenceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendances")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime checkInTime;

    @Enumerated(EnumType.STRING)
    private PresenceStatus status;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Activity activity;
}
