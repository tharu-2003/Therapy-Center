package com.ijse.gdse72.therapycenter.bo.custom;

import com.ijse.gdse72.therapycenter.bo.SuperBO;
import com.ijse.gdse72.therapycenter.dto.UserDTO;

public interface UserBO extends SuperBO {
    boolean authenticateUser(String userName, String password) throws Exception;
    UserDTO getUserByUsername(String userName) throws Exception;
    boolean saveUser(UserDTO userDTO) throws Exception;
}
