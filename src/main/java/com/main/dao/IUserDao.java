package com.main.dao;

import com.main.model.User;

public interface IUserDao {

    User selectUser(long id);
}