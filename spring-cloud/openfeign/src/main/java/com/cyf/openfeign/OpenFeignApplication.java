package com.cyf.openfeign;

import com.cyf.openfeign.client.Result;
import com.cyf.openfeign.client.TestClient;
import com.cyf.openfeign.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 陈一锋
 * @date 2022/10/21 11:49 下午
 */
@RestController
@EnableFeignClients
@SpringBootApplication
public class OpenFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenFeignApplication.class, args);
    }

    @Resource
    private TestClient testClient;

    @GetMapping("/test")
    public void test(){
        Result data = testClient.testJson();
        User u = data.getResultData(User.class);
        System.out.println(data);
    }


}
