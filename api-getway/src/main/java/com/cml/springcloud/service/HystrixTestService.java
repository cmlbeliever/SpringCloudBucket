package com.cml.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

@Component
public class HystrixTestService {
    public String defaultValue(String key) {
        return "defaultValue:" + key;
    }

    @HystrixCommand(fallbackMethod = "defaultValue")
    public String getTestValue(String key) {
        throw new IllegalArgumentException("key:" + key);
    }
}
