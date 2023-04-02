package com.cyf.openfeign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 陈一锋
 * @date 2022/10/21 11:50 下午
 */
@FeignClient(name = "testClient", url = "127.0.0.1:9091",configuration = ResultDecoder.class)
public interface TestClient {

    @GetMapping(value = "/testJson", consumes = "application/json")
    Result testJson();
}
