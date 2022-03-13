package com.cyf;

import com.cyf.model.User;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;

/**
 * @author 陈一锋
 * @date 2021/8/14 2:26 下午
 */
public class MetaObjectTest {

    @Test
    public void t1(){
        Configuration configuration = new Configuration();
        User user = new User(1,"cyf");
        MetaObject metaObject = configuration.newMetaObject(user);
        metaObject.setValue("id",2);
        System.out.println(metaObject.getValue("id"));
    }
}
