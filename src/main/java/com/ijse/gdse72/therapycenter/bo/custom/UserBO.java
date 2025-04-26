package com.ijse.gdse72.therapycenter.bo.custom;

import com.ijse.gdse72.therapycenter.bo.SuperBO;
import com.ijse.gdse72.therapycenter.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    boolean authenticateUser(String userName, String password) throws Exception;
    UserDTO getUserByUsername(String userName) throws Exception;
    boolean saveUser(UserDTO userDTO) throws Exception;
    UserDTO searchUser(String userId) throws Exception;
    boolean confirmation(String userId, String password) throws SQLException;
    boolean deleteUser(String userId) throws Exception;
    boolean updateUser(UserDTO userDTO) throws Exception;
    ArrayList<UserDTO> getAllUser() throws Exception;
    String getNextuserId() throws SQLException, ClassNotFoundException;
}
