package com.cml.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class DemoController {
	@Autowired
	private DiscoveryClient client;
	
	@ResponseBody
	@RequestMapping("/info")
	public Object info() {
		return client.getServices();
	}
	
	@ResponseBody
	@RequestMapping("/info2")
	public Object info2() {
		return client.getServices();
	}
}
