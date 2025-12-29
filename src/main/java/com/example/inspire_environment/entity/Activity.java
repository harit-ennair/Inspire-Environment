package com.example.inspire_environment.entity;

import com.example.inspire_environment.enums.ActivityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff managedBy;

    @OneToMany(mappedBy = "activity")
    private List<Task> tasks;

    @OneToMany(mappedBy = "activity")
    private List<Attendance> attendances;

}