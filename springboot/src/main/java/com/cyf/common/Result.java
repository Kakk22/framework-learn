package com.cyf.common;

import java.io.Serializable;

/**
 * @author 陈一锋
 * @date 2022/3/6 7:45 下午
 */
public class Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    public static Result<ExceptionCodeEnum> error(ExceptionCodeEnum error) {
        return new Result<>(ExceptionCodeEnum.ERROR.getCode(), error.getDesc());
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
