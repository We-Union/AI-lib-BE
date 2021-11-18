package com.main.service;

import com.main.model.User;

public interface UserService {

    User selectUserByID(long userId);
    User selectUserByUsername(String username);
    int addUser(User user);
}