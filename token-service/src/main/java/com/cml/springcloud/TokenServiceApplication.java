package com.cml.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
//@EnableDiscoveryClient
@SpringBootApplication
public class TokenServiceApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(TokenServiceApplication.class).web(true).run(args);
	}
}
