package com.ijse.gdse72.therapycenter.bo.custom.impl;

import com.ijse.gdse72.therapycenter.bo.custom.UserBO;
import com.ijse.gdse72.therapycenter.dao.DAOFactory;
import com.ijse.gdse72.therapycenter.dao.custom.UserDAO;
import com.ijse.gdse72.therapycenter.dto.UserDTO;
import com.ijse.gdse72.therapycenter.entity.User;
import com.ijse.gdse72.therapycenter.util.PasswordUtil;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = DAOFactory.getInstance().getDAO(DAOFactory.DaoType.USER);

    @Override
    public boolean authenticateUser(String userName, String password) throws Exception {
        User user = userDAO.findByUsername(userName);

        if (user != null) {
            boolean passwordMatches = PasswordUtil.checkPassword(password, user.getPassword());

            return passwordMatches;
        }
        return false;
    }

    @Override
    public UserDTO getUserByUsername(String userName) throws Exception {
        User user = userDAO.findByUsername(userName);
        if (user != null) {
            return new UserDTO(
                    user.getUserId(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getRole()
            );
        }
        return null;
    }

    @Override
    public boolean saveUser(UserDTO userDTO) throws Exception {
        if (userDAO.findByUsername(userDTO.getUserName()) != null) {
            return false;
        }

        String hashedPassword = PasswordUtil.hashPassword(userDTO.getPassword());
        System.out.println("Hashed password during registration: " + hashedPassword);

        User user = new User(userDTO.getUserId(), userDTO.getUserName(), hashedPassword, userDTO.getRole());
        userDAO.save(user);
        return true;
    }
}
