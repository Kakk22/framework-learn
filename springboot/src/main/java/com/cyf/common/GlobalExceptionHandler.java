package com.cyf.common;

import com.cyf.execption.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 陈一锋
 * @date 2022/3/6 8:41 下午
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 业务异常
     *
     */
    @ExceptionHandler(BizException.class)
    public Result<ExceptionCodeEnum> handleBizException(BizException bizException) {
        log.error("业务异常:{}", bizException.getMessage(), bizException);
        return Result.error(bizException.getError());
    }

    /**
     * 运行时异常
     *
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<ExceptionCodeEnum> handleRunTimeException(RuntimeException e) {
        log.error("运行时异常: {}", e.getMessage(), e);
        return Result.error(ExceptionCodeEnum.ERROR);
    }
}
