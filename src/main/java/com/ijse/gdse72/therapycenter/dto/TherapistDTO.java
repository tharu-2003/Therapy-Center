package com.ijse.gdse72.therapycenter.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class TherapistDTO {

    private String therapistId;
    private String name;
    private String specialization;
    private String availability;
    private int contactNumber;
    private String assignedProgram;
    private String email;

}
