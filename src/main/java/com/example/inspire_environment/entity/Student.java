package com.example.inspire_environment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "students")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {

    @Column(unique = true)
    private String studentCode;

    @OneToMany(mappedBy = "student")
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "student")
    private List<Submission> submissions ;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Presence> presences;


}