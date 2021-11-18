package com.main.service.impl;

import com.main.dao.ParameterDao;
import com.main.model.Parameter;
import com.main.service.ParameterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("parameterService")
public class ParameterServiceImpl implements ParameterService {

    @Resource
    private ParameterDao parameterDao;
    public Parameter selectParameterByID(long id)
    {
        return parameterDao.selectParameterById(id);
    }


}