package com.main.controller;

import com.main.model.User;
import com.main.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.main.utils.JsonData;

import java.io.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

//    @Autowired
//    IUserService userService;
//    @ResponseBody
//    @RequestMapping(value="/select",produces="application/json;charset=UTF-8")
//    public String selectUser(){
//        User user = userService.selectUser(1);
//        return JsonData.buildSuccess(user);
//    }
    @ResponseBody
    @RequestMapping(value="/select",produces="application/json;charset=UTF-8")
    public String selectUser() throws IOException, InterruptedException {
        String exe = "python";
        String command = "D:\\test.py";
        String[] cmdArr = new String[] {exe, command};
        Process process = Runtime.getRuntime().exec(cmdArr);
        InputStream is = process.getInputStream();
        String str = new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        process.waitFor();
        return str;
    }
}