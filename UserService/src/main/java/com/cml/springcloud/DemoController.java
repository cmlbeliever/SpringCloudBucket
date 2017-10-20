package com.cml.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class DemoController {

	@Value("${spring.application.name}")
	private String port;
	@Value("${server.port}")
	private String sererName;

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

	@ResponseBody
	@RequestMapping("/hello")
	public String sayHello(String req) {
		return "server:" + req + ",from : port:" + port + ",serverName:" + sererName;
	}

	@RequestMapping("/userInfo")
	@ResponseBody
	public String getOrder(String user) {
		return "Get userInfo[ " + user + "]  from port:" + port + ",serverName:" + sererName;
	}
}
