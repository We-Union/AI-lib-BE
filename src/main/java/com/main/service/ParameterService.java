package com.main.service;

import com.main.model.Parameter;

import java.util.List;

public interface ParameterService {
    Parameter selectParameterByID(long id);
    List<Parameter> selectParameterByModel(String model);
}
