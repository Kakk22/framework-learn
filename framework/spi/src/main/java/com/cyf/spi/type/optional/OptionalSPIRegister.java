package com.cyf.spi.type.optional;

import com.cyf.spi.FrameworkServiceLoader;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @author 陈一锋
 * @date 2023/4/16 1:22 PM
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionalSPIRegister {

    public <T extends OptionalSPI> Optional<T> findRegistryService(final Class<T> serviceClass) {
        return FrameworkServiceLoader.getService(serviceClass).stream().findFirst();
    }
}
