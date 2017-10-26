package com.cml.springcloud.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cml.springcloud.model.result.AuthResult;

@FeignClient("AUTH-SERVICE")
public interface AuthApi {

	@RequestMapping(value = "/auth/encodeToken")
	AuthResult encodeToken(@RequestParam("token") String token);

	@RequestMapping(value = "/auth/decodeToken")
	AuthResult parseToken(@RequestParam("token") String token);
}
