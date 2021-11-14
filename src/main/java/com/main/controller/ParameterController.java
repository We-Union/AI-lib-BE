package com.main.controller;

import com.main.utils.Configs;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parameter")
public class ParameterController {

    //    @Autowired
//    IUserService userService;
//    @ResponseBody
//    @RequestMapping(value="/select",produces="application/json;charset=UTF-8")
//    public String selectUser(){
//        User user = userService.selectUser(1);
//        return JsonData.buildSuccess(user);
//    }
    @ResponseBody
    @RequestMapping(value = "/select", produces = "application/json;charset=UTF-8")
    public String selectUser() throws IOException, InterruptedException {


        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        Configs c = (Configs) ctx.getBean("configs");
        String exe = c.getConfig("python_exec");
        String path = c.getConfig("python_path");
        String command = path + "test.py";
        System.out.println(command);
        String[] cmdArr = new String[]{exe, command};
        Process process = Runtime.getRuntime().exec(cmdArr);
        InputStream is = process.getInputStream();
        String str = new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        process.waitFor();
        return str;
    }
}
