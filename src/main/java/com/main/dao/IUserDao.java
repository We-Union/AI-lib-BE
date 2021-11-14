package com.main.dao;

import com.main.model.User;

public interface IUserDao {

    User selectUserById(long id);
    User selectUserByUsername(String username);
    int addUser(User user);
}