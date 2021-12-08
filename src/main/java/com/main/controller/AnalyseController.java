package com.main.controller;

import com.main.utils.Configs;
import com.main.utils.JsonData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
//@RequestMapping(value= "/analyse",produces = "application/json;charset=UTF-8")
public class AnalyseController {

    //    @Autowired
//    IUserService userService;
//    @ResponseBody
//    @RequestMapping(value="/select",produces="application/json;charset=UTF-8")
//    public String selectUser(){
//        User user = userService.selectUser(1);
//        return JsonData.buildSuccess(user);
//    }
    @ResponseBody
    @RequestMapping(value = "/analyse", produces = "application/json;charset=UTF-8")
    public String Analyse(HttpServletRequest request,@RequestBody Map<String,String> map) throws IOException, InterruptedException {
        HttpSession session = request.getSession();
        if(session.getAttribute("uid") == null) {
            return JsonData.buildError(4004, "你还未登录，请先登录");
        }
        String model = map.get("model");
        String paramater = map.get("parameter");

        if(model==null || paramater==null)
        {
            return JsonData.buildError(2001,"缺少参数");
        }


        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Configs c = (Configs) ctx.getBean("configs");
        String exe = c.getConfig("python_exec");
        String python_file = c.getConfig("python_file");
        String[] cmdArr = new String[]{exe, python_file,model,paramater};
        Process process = Runtime.getRuntime().exec(cmdArr);
        InputStream is = process.getInputStream();
        String str = new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        process.waitFor();
        return JsonData.buildSuccess(str);
    }
}
