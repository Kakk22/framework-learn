package com.cyf.nettybook.protocol.codec;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.*;

import java.io.IOException;

/**
 * @author 陈一锋
 * @date 2021/1/14 20:48
 **/
public class MarshallingCodecFactory {
    /**
     * 创建Jboss Marshaller
     */
    protected static Marshaller buildMarshalling() throws IOException {
        final MarshallerFactory marshallerFactory = Marshalling
                .getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        return marshallerFactory
                .createMarshaller(configuration);
    }

    /**
     * 创建Jboss Unmarshaller
     */
    protected static Unmarshaller buildUnMarshalling() throws IOException {
        final MarshallerFactory marshallerFactory = Marshalling
                .getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        return marshallerFactory
                .createUnmarshaller(configuration);
    }
}
