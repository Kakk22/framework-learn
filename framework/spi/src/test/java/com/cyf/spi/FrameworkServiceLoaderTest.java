package com.cyf.spi;

import com.cyf.spi.typed.TypeSPIService;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;


/**
 * @author 陈一锋
 * @date 2023/4/16 1:27 PM
 */
public class FrameworkServiceLoaderTest {

    static {
        FrameworkServiceLoader.registry(TypeSPIService.class);
    }


    @Test
    public void getService() {
        Collection<TypeSPIService> collection = FrameworkServiceLoader.getService(TypeSPIService.class);
        Assert.assertEquals(collection.size(), 1);
    }
}