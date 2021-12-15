package com.main.dao;

import com.main.model.Parameter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParameterDao {
    Parameter selectParameterById(long id);
    List<Parameter> selectParameterByModel(@Param("uid")long uid,@Param("model")String model);
    Parameter selectParameterDefault(String model);
    int addParameter(Parameter parameter);
    int  deleteParameter(long id);
}
