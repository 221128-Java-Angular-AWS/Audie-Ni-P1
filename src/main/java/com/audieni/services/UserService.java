package com.audieni.services;

import com.audieni.models.User;
import com.audieni.models.UserDAO;

public class UserService {
    private UserDAO dao;

    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    public void registerUser(User user) {
        dao.create(user);
    }

    public void deleteUser(User user) {
        dao.delete(user);
    }
}
