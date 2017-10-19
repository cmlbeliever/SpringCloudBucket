package com.cml.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;

public class DemoController {
	@Autowired
	private DiscoveryClient client;
}
