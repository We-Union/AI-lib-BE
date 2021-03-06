package com.main.service.impl;

import com.main.dao.ParameterDao;
import com.main.model.Parameter;
import com.main.service.ParameterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("parameterService")
public class ParameterServiceImpl implements ParameterService {

    @Resource
    private ParameterDao parameterDao;
    public Parameter selectParameterByID(long id)
    {
        return parameterDao.selectParameterById(id);
    }
    public List<Parameter> selectParameterByModel(long uid,String model)
    {
        return parameterDao.selectParameterByModel(uid,model);
    }
    public Parameter selectParameterDefault(String model)
    {
        return parameterDao.selectParameterDefault(model);
    }
    public int addParameter(Parameter parameter)
    {
        return parameterDao.addParameter(parameter);
    }
    public int deleteParameter(long id)
    {
        return parameterDao.deleteParameter(id);
    }
    public int updateParameter(Parameter parameter)
    {
        return parameterDao.updateParameter(parameter);
    }
    public List<Parameter> selectParameterByName(long uid,String model,String name)
    {
        return parameterDao.selectParameterByName(uid,model,name);
    }



}