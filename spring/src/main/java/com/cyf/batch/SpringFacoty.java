package com.cyf.batch;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author 陈一锋
 * @date 2021/9/21 6:04 下午
 */

class SpringFactory implements ApplicationContextAware {

    static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringFactory.applicationContext = applicationContext;
    }

    static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}