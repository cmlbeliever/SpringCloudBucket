package com.cml.springcloud.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("ORDER-SERVICE")
public interface OrderApi {

    @RequestMapping(value = "/order")
    String getOrder(@RequestParam("user") String user);

    @RequestMapping(value = "/testRetry")
    String testRetry();
}
