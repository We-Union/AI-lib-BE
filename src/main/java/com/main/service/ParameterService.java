package com.main.service;

import com.main.model.Parameter;

import java.util.List;

public interface ParameterService {
    Parameter selectParameterByID(long id);
    List<Parameter> selectParameterByModel(long uid,String model);
    Parameter selectParameterDefault(String model);
    int addParameter(Parameter parameter);
    int deleteParameter(long id);
    int updateParameter(Parameter parameter);
}
