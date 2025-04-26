package com.ijse.gdse72.therapycenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "therapists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Therapist {

    @Id
    @Column(name = "therapist_id", length = 10)
    private String therapistId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String specialization;

    @Column(nullable = false, length = 50)
    private String availability;

    @Column(nullable = false, length = 15)
    private int contactNumber;

    @Column(length = 100)
    private String assignedProgram ;

    @Column(length = 100)
    private String email;

    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TherapySession> therapySessions = new ArrayList<>();

    public Therapist(String therapistId, String name, String specialization, String availability, int contactNumber, String assignedProgram, String email) {
        this.therapistId = therapistId;
        this.name = name;
        this.specialization = specialization;
        this.availability = availability;
        this.contactNumber = contactNumber;
        this.assignedProgram = assignedProgram;
        this.email = email;
    }
}