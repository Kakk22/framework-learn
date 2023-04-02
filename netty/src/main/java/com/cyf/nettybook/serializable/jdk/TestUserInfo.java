package com.cyf.nettybook.serializable.jdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author 陈一锋
 * @date 2021/1/7 14:13
 **/
public class TestUserInfo {

    public static void main(String[] args) {
        UserInfo user = new UserInfo();
        user.buildUserId(11).buildUsername("welcome to netty");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);) {
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            byte[] b = out.toByteArray();
            // jdk serialize result length is 124
            System.out.println("jdk serializable length is:" + b.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // byteBuffer  result is 24
        System.out.println("the byte array serializable length is:" + user.codeC().length);

    }
}
