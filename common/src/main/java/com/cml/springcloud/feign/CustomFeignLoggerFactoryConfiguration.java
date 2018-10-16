package com.cml.springcloud.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: cml
 * @Date: 2018-10-12 14:00
 * @Description:
 */
@Configuration
public class CustomFeignLoggerFactoryConfiguration {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.FULL;
    }

    @Bean
    @ConditionalOnMissingBean
    public FeignLogInterceptor feignLogInterceptor() {
        return (String name, String url, String requestBody, Integer status, String body, long interval) -> {
            logger.debug(String.format("name:%s,url:%s,requestBody:%s,Response:%s,Interval:%s", name, url, requestBody, body, interval));
        };
    }

    @Bean
    public DefaultFeignLogger defaultFeignLogger(FeignLogInterceptor feignLogInterceptor) {
        return new DefaultFeignLogger(feignLogInterceptor);
    }

    @Bean
    public FeignLoggerFactory feignLoggerFactory(DefaultFeignLogger defaultFeignLogger) {
        return new org.springframework.cloud.openfeign.DefaultFeignLoggerFactory(defaultFeignLogger);
    }
}
