package com.main.utils;


import java.util.Map;

public class Configs
{
    public Map<String,String> configs;
    public String getConfig(String key)
    {
        return configs.get(key);
    }
    public Map<String,String> getConfigs()
    {
        return configs;
    }
    public void setConfigs(Map<String, String>_configs)
    {
        configs=_configs;
    }

}
