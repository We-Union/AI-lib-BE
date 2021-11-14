package com.main.service.impl;

import com.main.dao.IUserDao;
import com.main.model.User;
import com.main.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    public User selectUserByID(long userId)
    {
        return userDao.selectUserById(userId);
    }

    public User selectUserByUsername(String username)
    {
        return userDao.selectUserByUsername(username);
    }
    public int addUser(User user)
    {
        return userDao.addUser(user);
    }

}