package com.cml.springcloud.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cml.springcloud.model.AuthModel;

@FeignClient("AUTH-CENTER")
public interface AuthApi {

	@RequestMapping(value = "/auth/encodeToken")
	AuthModel encodeToken(@RequestParam("token") String token);

	@RequestMapping(value = "/auth/decodeToken")
	AuthModel parseToken(@RequestParam("token") String token);
}
