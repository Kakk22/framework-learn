package com.cyf.codegen.context;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * @author 陈一锋
 * @date 2022/9/12 10:13 下午
 */
public class ProcessingEnvironmentHolder {
    public static final ThreadLocal<ProcessingEnvironment> environment = new ThreadLocal<>();


    public static void setEnvironment(ProcessingEnvironment pe){
        environment.set(pe);
    }

    public static ProcessingEnvironment getEnvironment(){
        return environment.get();
    }
}
