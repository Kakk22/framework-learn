package com.cyf.spi.type.typed;

import com.cyf.spi.FrameworkServiceLoader;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Optional;

/**
 * @author 陈一锋
 * @date 2023/4/16 12:38 PM
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TypeSPIRegistry {

    static {
        FrameworkServiceLoader.registry(TypeSPI.class);
    }

    public <T extends TypeSPI> Optional<T> findRegisterService(Class<T> serviceInterface, String type) {
        Collection<T> services = FrameworkServiceLoader.getService(serviceInterface);
        for (T service : services) {
            if (service.type().equals(type)) {
                return Optional.of(service);
            }
        }
        return Optional.empty();
    }

}
