package com.main.dao;

import com.main.model.Parameter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParameterDao {
    Parameter selectParameterById(long id);
    List<Parameter> selectParameterByModel(@Param("uid")long uid,@Param("model")String model);
    List<Parameter> selectParameterByName(@Param("uid")long uid,@Param("model")String model,@Param("name")String name);
    Parameter selectParameterDefault(String model);
    int addParameter(Parameter parameter);
    int  deleteParameter(long id);
    int updateParameter(Parameter parameter);
}
