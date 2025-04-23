package com.ijse.gdse72.therapycenter.dto.tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserTM {

    private String userId;
    private String userName;
    private String password;
    private String role;

}