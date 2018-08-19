package com.cml.springcloud.ribbon;

import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {
    @Bean
    public LoadBalancedRetryFactory loadBalancedRetryPolicyFactory(final SpringClientFactory clientFactory) {
        return new DefaultRibbonLoadBalanceRetryFactory(clientFactory);
    }
}
