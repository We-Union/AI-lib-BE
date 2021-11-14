package com.main.controller;

import com.main.model.User;
import com.main.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.main.utils.JsonData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.*;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import com.main.utils.Configs;
@RestController
@RequestMapping("/user")
public class UserController {
    private Boolean checkUsername(String username)
    {
        return true;
    }
    private Boolean checkPassword(String password)
    {
        return true;
    }
    private Boolean checkNickname(String nickname)
    {
        return true;
    }


    @Autowired
    IUserService userService;
    @ResponseBody
    @RequestMapping(value="/register",produces="application/json;charset=UTF-8",method = RequestMethod.POST)
    public String Reegister(HttpServletRequest request)
    {
        String username =  request.getParameter("username");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");

        if(checkNickname(nickname) && checkUsername(username) && checkPassword(password))
        {
            User user = new User();
            user.setUsername(username);
            user.setNickname(nickname);
            user.setPassword(password);
            int result = userService.addUser(user);
            return JsonData.buildSuccess();
        }
        else
        {
            return JsonData.buildError(2002,"参数格式不正确");
        }




    }

    @ResponseBody
    @RequestMapping(value="/me",produces="application/json;charset=UTF-8")
    public String me(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        if(session.getAttribute("uid") != null)
        {
            long uid = (long)session.getAttribute("uid");
            User user = userService.selectUserByID(uid);
            return JsonData.buildSuccess(user);
        }
        else
        {
            return JsonData.buildError(4004,"你还未登录，请先登录");
        }

    }

    @ResponseBody
    @RequestMapping(value="/login",produces="application/json;charset=UTF-8",method = RequestMethod.POST)
    public String login(HttpServletRequest request)
    {
        String username =  request.getParameter("username");
        User user = userService.selectUserByUsername(username);
        if (user == null)
        {

            return JsonData.buildError(4004,"用户名或密码错误");
        }
        String password = request.getParameter("password");
        if (user.checkPassword(password))
        {
            HttpSession session = request.getSession(true);
            session.setAttribute("uid",user.getId() );
            return JsonData.buildSuccess(user);
        }
        else
        {
            return JsonData.buildError(4004,"用户名或密码错误");
        }

    }



}

