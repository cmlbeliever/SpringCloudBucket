package com.cml.learn.springcloud.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/**
 * @Auther: cml
 * @Date: 2019-01-31 13:57
 * @Description:
 */
@Configuration
public class CustomFilter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    @Order(-1)
    public GlobalFilter requestLogFilter() {
        return (exchange, chain) -> {
            logger.info("first pre filter:" + exchange.getRequest().getURI());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("third post filter finish");
            }));
        };
    }

}
