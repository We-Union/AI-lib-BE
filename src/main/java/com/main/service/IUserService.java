package com.main.service;

import com.main.model.User;

public interface IUserService {

    public User selectUserByID(long userId);
    public User selectUserByUsername(String username);
    public int addUser(User user);
}