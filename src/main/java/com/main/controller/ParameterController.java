package com.main.controller;
import com.main.model.Parameter;
import com.main.model.User;
import com.main.service.ParameterService;
import com.main.service.UserService;
import com.main.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import com.main.model.User;
import com.main.service.UserService;
@RestController
@RequestMapping("/parameter")
public class ParameterController {
    @Autowired
    ParameterService parameterService;
    @Autowired
    UserService userService;
    @ResponseBody
    @RequestMapping(value="/getbyid",produces="application/json;charset=UTF-8",method = RequestMethod.GET)
    public String getParameterByid(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        if(session.getAttribute("uid") == null) {
            return JsonData.buildError(4004, "你还未登录，请先登录");
        }
        if(request.getParameter("id")==null)
        {
            return JsonData.buildError(2001,"缺少参数");
        }

        long id = Long.parseLong(request.getParameter("id"));
        Parameter parameter = parameterService.selectParameterByID(id);
        if(parameter == null)
        {
            return JsonData.buildError(4004,"参数不存在");
        }

        long uid = (long)session.getAttribute("uid");
        if(parameter.getUid()!=uid)
        {
            return JsonData.buildError(4003,"您无权访问此参数");
        }

        return JsonData.buildSuccess(parameter);

    }

    @ResponseBody
    @RequestMapping(value="/getbymodel",produces="application/json;charset=UTF-8",method = RequestMethod.GET)
    public String getParameterByModel(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        if(session.getAttribute("uid") == null) {
            return JsonData.buildError(4004, "你还未登录，请先登录");
        }
        if(request.getParameter("model")==null)
        {
            return JsonData.buildError(2001,"缺少参数");
        }

        long uid =Long.parseLong(session.getAttribute("uid").toString());
        List<Parameter> parameters = parameterService.selectParameterByModel(uid,request.getParameter("model"));
        return JsonData.buildSuccess(parameters);

    }

    @ResponseBody
    @RequestMapping(value="/default",produces="application/json;charset=UTF-8",method = RequestMethod.GET)
    public String getParameterDefault(HttpServletRequest request)
    {
        if(request.getParameter("model")==null)
        {
            return JsonData.buildError(2001,"缺少参数");
        }


        Parameter parameters = parameterService.selectParameterDefault(request.getParameter("model"));
        return JsonData.buildSuccess(parameters);

    }
    @ResponseBody
    @RequestMapping(value="/delete",produces="application/json;charset=UTF-8",method = RequestMethod.DELETE)
    public String deleteParameter(HttpServletRequest request,@RequestBody Map<String,String> map)
    {
        HttpSession session = request.getSession();
        if(session.getAttribute("uid") == null) {
            return JsonData.buildError(4004, "你还未登录，请先登录");
        }
        if(map.get("id")==null)
        {
            return JsonData.buildError(2001,"缺少参数");
        }

        long id = Long.parseLong(map.get("id"));
        Parameter parameter = parameterService.selectParameterByID(id);
        if(parameter == null)
        {
            return JsonData.buildError(4004,"参数不存在");
        }

        long uid = (long)session.getAttribute("uid");
        if(parameter.getUid()!=uid)
        {
            return JsonData.buildError(4003,"您无权访问此参数");
        }
        int result = parameterService.deleteParameter(id);
        if(result==0)
        {
            return JsonData.buildError(5001,"数据库错误:"+ result);
        }
        return JsonData.buildSuccess();



    }
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=UTF-8",method = RequestMethod.POST)
    public String addParameter(HttpServletRequest request,@RequestBody Map<String,String> map)
    {
        HttpSession session = request.getSession();
        if(session.getAttribute("uid") == null) {
            return JsonData.buildError(4004, "你还未登录，请先登录");
        }
        long uid = (long)session.getAttribute("uid");
        String name = map.get("name");
        String model = map.get("model");
        String value = map.get("value");
        if(name==null || model == null || value == null)
        {
            return JsonData.buildError(2001,"缺少参数");
        }
        if(name.equals("默认"))
        {
            return JsonData.buildError(2002,"为避免混淆，请勿使用”默认“作为参数名称");
        }
        List <Parameter> get_by_name = parameterService.selectParameterByName(uid,model,name);
        if (get_by_name.size() !=0 )
        {
            return JsonData.buildError(2002,"已存在相同名称参数");
        }
        Parameter parameter = new Parameter();
        parameter.setName(name);
        parameter.setModel(model);
        parameter.setType("general");
        parameter.setUid(uid);
        parameter.setValue(value);
        int result = parameterService.addParameter(parameter);
        return JsonData.buildSuccess();
    }

    @ResponseBody
    @RequestMapping(value="/adddefault",produces="application/json;charset=UTF-8",method = RequestMethod.POST)
    public String addDefaultParameter(HttpServletRequest request,@RequestBody Map<String,String> map)
    {
        HttpSession session = request.getSession();
        if(session.getAttribute("uid") == null) {
            return JsonData.buildError(4004, "你还未登录，请先登录");
        }
        long uid = (long)session.getAttribute("uid");
        String model = map.get("model");
        String value = map.get("value");
        if(model == null || value == null)
        {
            return JsonData.buildError(2001,"缺少参数");
        }

        User user = userService.selectUserByID(uid);
        if(user.getType()!=1 )
        {
            return JsonData.buildError(4003,"只有超级管理员才有权添加默认参数");
        }
        if(parameterService.selectParameterDefault(model) != null)
        {
            return JsonData.buildError(4003,"默认参数已存在，请删除后重新添加");
        }
        Parameter parameter = new Parameter();
        parameter.setName("默认");
        parameter.setModel(model);
        parameter.setType("default");
        parameter.setUid(uid);
        parameter.setValue(value);
        int result = parameterService.addParameter(parameter);
        return JsonData.buildSuccess();
    }
    @ResponseBody
    @RequestMapping(value="/update",produces="application/json;charset=UTF-8",method = RequestMethod.PUT)
    public String updateParameter(HttpServletRequest request,@RequestBody Map<String,String> map)
    {
        HttpSession session = request.getSession();
        if(session.getAttribute("uid") == null) {
            return JsonData.buildError(4004, "你还未登录，请先登录");
        }
        if(map.get("id")==null || map.get("value") == null)
        {
            return JsonData.buildError(2001,"缺少参数");
        }

        long id = Long.parseLong(map.get("id"));
        Parameter parameter = parameterService.selectParameterByID(id);
        if(parameter == null)
        {
            return JsonData.buildError(4004,"参数不存在");
        }

        long uid = (long)session.getAttribute("uid");
        if(parameter.getUid()!=uid)
        {
            return JsonData.buildError(4003,"您无权访问此参数");
        }
        parameter.setValue(map.get("value"));
        int result = parameterService.updateParameter(parameter);
        return JsonData.buildSuccess();
    }
}

