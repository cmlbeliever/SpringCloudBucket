package com.cml.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class EurakeServerApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(EurakeServerApplication.class).web(true).run(args);
	}
}
