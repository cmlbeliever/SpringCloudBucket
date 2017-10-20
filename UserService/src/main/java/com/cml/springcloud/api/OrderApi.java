package com.cml.springcloud.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("EUREKA-ORDER")
public interface OrderApi {

	@RequestMapping(value = "/order")
	String getOrder(@RequestParam("user") String user);
}
