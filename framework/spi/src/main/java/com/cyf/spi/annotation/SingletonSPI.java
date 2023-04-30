package com.cyf.spi.annotation;

import java.lang.annotation.*;

/**
 * @author 陈一锋
 * @date 2023/4/16 12:24 PM
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SingletonSPI {
}
