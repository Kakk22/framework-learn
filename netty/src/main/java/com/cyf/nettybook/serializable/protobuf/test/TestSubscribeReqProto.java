package com.cyf.nettybook.serializable.protobuf.test;

import com.cyf.nettybook.serializable.protobuf.pojo.SubscribeReqProto;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;

/**
 * @author 陈一锋
 * @date 2021/1/7 16:37
 **/
public class TestSubscribeReqProto {

    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(1);
        builder.setUserName("chenyifeng");
        builder.setProductName("netty");
        ArrayList<String> address = new ArrayList<>();
        address.add("nanjin");
        address.add("beijin");
        address.add("shenzhen");

        builder.setAddress("beijin");
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("before encode:" + req.toString());
        SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
        System.out.println("after decode :" + req.toString());
        System.out.println("assert equal:--->" + req2.equals(req));
    }
}
