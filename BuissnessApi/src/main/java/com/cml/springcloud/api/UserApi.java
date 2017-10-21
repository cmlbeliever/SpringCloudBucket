package com.cml.springcloud.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("EUREKA-USER")
public interface UserApi {

	@RequestMapping(value = "/getUser")
	String getUser(@RequestParam("user") String user);
}
