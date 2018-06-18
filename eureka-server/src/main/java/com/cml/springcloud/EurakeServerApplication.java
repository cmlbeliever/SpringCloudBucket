package com.cml.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurakeServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurakeServerApplication.class).web(true).run(args);
    }
}
