package com.cml.springcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication()
public class UserClientApplication {
    static Logger logger = LoggerFactory.getLogger(UserClientApplication.class);

    private static final String CUSTOM_COUNTER = "xxl_my_counter";

    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext run = new SpringApplicationBuilder(UserClientApplication.class).web(true).run(args);

        } catch (Exception e) {
            logger.error("", e);
        }
    }

//    @Bean
//    public ApplicationRunner applicationRunner() {
//        return (arg) -> {
//            new Thread(() -> {
//                for (int i = 0; i < 10000; i++) {
//                    Metrics.counter(CUSTOM_COUNTER, "test", "失败的次数").increment((int) (100 * Math.random()));
//                    Metrics.counter(CUSTOM_COUNTER, "test2", "成功的次数").increment((int) (100 * Math.random()));
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        };
//    }

    @Configuration
    public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().permitAll()
                    .and().csrf().disable();
        }
    }
}
