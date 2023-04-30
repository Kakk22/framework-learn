package com.cyf.spi.type.typed;

/**
 * 根据类型的SPI
 *
 * @author 陈一锋
 * @date 2023/4/16 12:35 PM
 */
public interface TypeSPI {

    default String type() {
        return "";
    }
}
