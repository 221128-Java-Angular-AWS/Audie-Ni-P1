package com.audieni.services;

import com.audieni.exceptions.ExistingUserException;
import com.audieni.exceptions.IncorrectPasswordException;
import com.audieni.exceptions.UserNotFoundException;
import com.audieni.models.User;
import com.audieni.models.UserDAO;

public class UserService {
    private final UserDAO dao;

    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    public void registerUser(User user) throws ExistingUserException {
        if (!dao.checkExistingAccount(user.getEmail())) {
            dao.create(user);
        }
    }

    public User loginUser(String email, String password) throws UserNotFoundException, IncorrectPasswordException {
        return dao.authenticate(email, password);
    }

    public User logoutUser(User user) {
        return null;
    }

    public void deleteUser(User user) throws ExistingUserException {
        if (!dao.checkExistingAccount(user.getEmail())) {
            dao.delete(user);
        }
    }
}
