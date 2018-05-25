package com.cml.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cml.springcloud.api.OrderApi;

@Controller
@RequestMapping("/")
public class DemoController {

	@Value("${spring.application.name:test}")
	private String port;
	@Value("${server.port:555}")
	private String sererName;

	@Autowired
	private DiscoveryClient client;


	@ResponseBody
	@RequestMapping("/info")
	public Object info() {
		return client.getServices();
	}

}
