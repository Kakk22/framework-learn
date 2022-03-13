package com.cyf.common;

import com.cyf.execption.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 陈一锋
 * @date 2022/3/6 8:41 下午
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 业务异常
     */
    @ExceptionHandler(BizException.class)
    public Result<ExceptionCodeEnum> handleBizException(BizException bizException, HandlerMethod handlerMethod) {
        log.error("业务异常:{},当前类:{},请求方法:{}", bizException.getMessage(), handlerMethod.getBeanType().getSimpleName(), handlerMethod.getMethod().getName());
        return Result.error(bizException.getError());
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<ExceptionCodeEnum> handleRunTimeException(RuntimeException e, HandlerMethod handlerMethod) {
        log.error("运行时异常:{},当前类:{},请求方法:{}", e.getMessage(), handlerMethod.getBeanType().getSimpleName(), handlerMethod.getMethod().getName(), e);
        return Result.error(ExceptionCodeEnum.ERROR);
    }
}
