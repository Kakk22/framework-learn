package com.cyf.spi;

import com.cyf.spi.annotation.SingletonSPI;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 陈一锋
 * @date 2023/4/16 11:31 AM
 */
public final class FrameworkServiceLoader {

    private static final Map<Class<?>, Collection<Object>> SERVICES = new ConcurrentHashMap<>();

    public static <T> void registry(Class<T> serviceInterface) {
        if (!SERVICES.containsKey(serviceInterface)) {
            Collection<Object> collection = load(serviceInterface);
            SERVICES.putIfAbsent(serviceInterface, collection);
        }
    }

    public static <T> Collection<Object> load(Class<T> serviceInterface) {
        Collection<Object> result = new ArrayList<>();
        for (T t : ServiceLoader.load(serviceInterface)) {
            result.add(t);
        }
        return result;
    }

    public static <T> Collection<T> getService(Class<T> serviceInterface) {
        return serviceInterface.getAnnotation(SingletonSPI.class) == null ? createNewService(serviceInterface) : getSingleService(serviceInterface);
    }

    @SuppressWarnings("unchecked")
    private static <T> Collection<T> getSingleService(Class<T> serviceInterface) {
        //single
        return (Collection<T>) SERVICES.getOrDefault(serviceInterface, Collections.emptyList());
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private static <T> Collection<T> createNewService(Class<T> serviceInterface) {
        if (!SERVICES.containsKey(serviceInterface)) {
            //no register
            return Collections.emptyList();
        }
        Collection<Object> service = SERVICES.get(serviceInterface);
        if (service.isEmpty()) {
            return Collections.emptyList();
        }
        Collection<T> result = new ArrayList<>();
        for (Object o : service) {
            result.add((T) o.getClass().getDeclaredConstructor().newInstance());
        }
        return result;
    }
}
