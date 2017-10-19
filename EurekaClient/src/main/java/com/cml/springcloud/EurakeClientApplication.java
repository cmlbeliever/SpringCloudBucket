package com.cml.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EurakeClientApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(EurakeClientApplication.class).web(true).run(args);
	}
}
