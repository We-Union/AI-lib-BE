package com.main.controller;

import com.main.model.User;
import com.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.main.utils.JsonData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.regex.Pattern;

@RestController

@RequestMapping("/user")

public class UserController {
    private Boolean checkUsername(String username)
    {
        if(username == null)
            return false;
       return Pattern.matches("^[a-zA-Z0-9]{6,15}$", username);
    }
    private Boolean checkPassword(String password)
    {
        if(password == null)
            return false;
        return Pattern.matches("^[a-zA-Z0-9.@$!%*#_~?&^]{8,18}$", password);
    }
    private Boolean checkNickname(String nickname)
    {
        if(nickname == null)
            return false;
        return Pattern.matches("^[a-zA-Z0-9\u4e00-\u9fa5_-]{2,15}$", nickname);
    }


    @Autowired
    UserService userService;
    @ResponseBody
    @RequestMapping(value="/register",produces="application/json;charset=UTF-8",method = RequestMethod.POST)

    public String Reegister(HttpServletRequest request,@RequestBody Map<String,String> map)
    {
        String username = map.get("username");
        String password = map.get("password");
        String nickname = map.get("nickname");

        if(checkNickname(nickname) && checkUsername(username) && checkPassword(password))
        {
            User select_result = userService.selectUserByUsername(username);
            if(select_result==null)
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
                return JsonData.buildError(2003,"用户名已存在");
            }

        }
        else
        {
            return JsonData.buildError(2002,"参数格式不正确");
        }

    }

    @ResponseBody
    @RequestMapping(value="/me",produces="application/json;charset=UTF-8",method = RequestMethod.GET)
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
    public String login(HttpServletRequest request,@RequestBody Map<String,String> map)
    {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("uid") != null)
        {
            return JsonData.buildError(4003,"您已经登录！");
        }
        String username =  map.get("username");
        User user = userService.selectUserByUsername(username);
        if (user == null)
        {

            return JsonData.buildError(4004,"用户名或密码错误");
        }
        String password = map.get("password");
        if (user.checkPassword(password))
        {
            session.setAttribute("uid",user.getId() );
            return JsonData.buildSuccess(user);
        }
        else
        {
            return JsonData.buildError(4004,"用户名或密码错误");
        }

    }

    @ResponseBody
    @RequestMapping(value="/logout",produces="application/json;charset=UTF-8",method = RequestMethod.GET)
    public String logout(HttpServletRequest request)
    {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("uid") == null)
        {
            return JsonData.buildError(4003,"您还未登录！");
        }
        session.removeAttribute("uid");
        return JsonData.buildSuccess();

    }
}

