package com.cyf.plugin;

/**
 * @author 陈一锋
 * @date 2021/9/4 10:37 下午
 */
public class PageHelper {

    private static final ThreadLocal<Page> THREAD_LOCAL = new ThreadLocal<>();

    public static void start(int pageSize, int pageNum) {
        Page page = new Page(pageSize, pageNum);
        THREAD_LOCAL.set(page);
    }

    public static Page get(){
        return THREAD_LOCAL.get();
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
