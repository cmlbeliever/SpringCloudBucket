package com.cml.springcloud.api;

import com.cml.springcloud.api.fallback.UserApiFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "USER-SERVICE", fallback = UserApiFallback.class)
public interface UserApi {

    @RequestMapping(value = "/getUser")
    String getUser(@RequestParam("user") String user);

    @RequestMapping(value = "/getUser1")
    String getUser1(@RequestParam("user") String user);
}
