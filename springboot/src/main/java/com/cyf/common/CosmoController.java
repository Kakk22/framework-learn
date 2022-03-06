package com.cyf.common;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 陈一锋
 * @date 2022/3/6 7:49 下午
 */
@RestController
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CosmoController {
}
