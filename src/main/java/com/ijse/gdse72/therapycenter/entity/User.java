package com.ijse.gdse72.therapycenter.entity;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class User {
    @Id
    private String userId;
    private String userName;
    private String password;
    private String role;

}