package com.cml.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cml.springcloud.api.UserApi;

@RefreshScope
@Controller
@RequestMapping("/")
public class DemoController {

	@Value("${server.port}")
	private String port;

	@Value("${spring.application.name}")
	private String sererName;

	@Value("${system.order.serverName}")
	private String serverName;

	@Autowired
	private DiscoveryClient client;

	@Autowired
	private UserApi userApi;

	@ResponseBody
	@RequestMapping("/test")
	public String test(String user) {
		return "port:" + port + ",serverName:" + sererName + ",getUser info ==>" + userApi.getUser(user);
	}

	@ResponseBody
	@RequestMapping("/infoTest")
	public Object info() {
		return client.getServices() + ",serverName:" + serverName;
	}

	@ResponseBody
	@RequestMapping("/info2")
	public Object info2() {
		return client.getServices();
	}

	@ResponseBody
	@RequestMapping("/hello")
	public String sayHello(String req) {
		return "req:" + req + ",from : port:" + port + ",serverName:" + sererName;
	}

	@RequestMapping("/order")
	@ResponseBody
	public String getOrder(String user) {
		return "Get user[ " + user + "] order from port:" + port + ",serverName:" + sererName;
	}
}
