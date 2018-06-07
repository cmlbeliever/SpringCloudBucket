package com.cml.springcloud;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableAdminServer
public class AdminMetricApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(AdminMetricApplication.class).web(true).run(args);
	}
}
