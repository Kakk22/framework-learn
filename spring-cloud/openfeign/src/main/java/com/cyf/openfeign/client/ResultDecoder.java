package com.cyf.openfeign.client;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cyf.openfeign.model.User;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * @author 陈一锋
 * @date 2022/10/22 12:39 上午
 */
public class ResultDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (!(type instanceof Class) && !(type instanceof ParameterizedType) && !(type instanceof WildcardType)) {
            throw new DecodeException(response.status(), "type is not an instance of Class or ParameterizedType: " + type, response.request());
        } else {
            String result = IOUtils.toString(response.body().asInputStream());
            //,new TypeReference<Result<User>>(){}
            return JSONObject.parseObject(result, type);
        }

    }
}
