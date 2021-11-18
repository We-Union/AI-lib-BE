package com.main.dao;

import com.main.model.Parameter;

import java.util.List;

public interface ParameterDao {
    Parameter selectParameterById(long id);
    List<Parameter> selectParameterByModel(String model);
}
