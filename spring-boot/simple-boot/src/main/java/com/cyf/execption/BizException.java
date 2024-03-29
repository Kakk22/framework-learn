package com.cyf.execption;

import com.cyf.common.ExceptionCodeEnum;
import lombok.Getter;

/**
 * @author 陈一锋
 * @date 2022/3/6 8:36 下午
 */
@Getter
public class BizException extends RuntimeException {
    private ExceptionCodeEnum error;

    /**
     * 构造器，有时我们需要将第三方异常转为自定义异常抛出，但又不想丢失原来的异常信息，此时可以传入cause
     *
     * @param error
     * @param cause
     */
    public BizException(ExceptionCodeEnum error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    /**
     * 构造器，只传入错误枚举
     *
     * @param error
     */
    public BizException(ExceptionCodeEnum error) {
        this.error = error;
    }
}
