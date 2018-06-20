package com.cml.springcloud;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EnableAdminServer
public class AdminMetricApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AdminMetricApplication.class).web(true).run(args);
    }

    @Autowired
    private DiscoveryClient client;

    @Bean
    public ApplicationRunner test9() {
        return args -> {
            Thread.sleep(20000);
            System.out.println("====================" + client.getServices());
        };
    }

    @Configuration
    public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().permitAll()
                    .and().csrf().disable();
        }
    }
}
