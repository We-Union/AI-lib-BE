package com.main.utils;
import com.alibaba.fastjson.JSON;

public class JsonData {
    /**
     * 状态码 0表示成功过，1表示处理中，-1 表示失败
     */
    private Integer code;
    /**
     * 业务数据
     */
    private Object data;
    /**
     * 信息表示
     */
    private String msg;

    public JsonData() {
    }

    public JsonData(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 成功，不用返回数据
     */
    public static String buildSuccess() {

        return JSON.toJSONString(new JsonData(0, null, null));
    }

    /**
     * 成功，返回数据
     * @return
     */
    public static String buildSuccess(Object data) {
        return JSON.toJSONString(new JsonData(0, data, null));
    }


    /**
     * 失败，自定义错误码和信息
     */
    public static String buildError(Integer code, String msg)
    {
        return JSON.toJSONString(new JsonData(code, null, msg));
    }
}