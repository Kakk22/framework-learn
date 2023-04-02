package com.cyf.openfeign.client;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * @author 陈一锋
 * @date 2022/3/6 7:45 下午
 */
public class Result implements Serializable {
    private Integer code;
    private String message;
    private String data;

    public Result() {
    }


    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public <T> T getResultData(Class<T> clazz){
        if (data != null){
            return JSONObject.parseObject(data,clazz);
        }else {
            return null;
        }
    }
}
