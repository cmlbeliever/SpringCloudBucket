package com.cml.springcloud.log;

import com.cml.springcloud.feign.FeignLogInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ElkFeignLogInterceptor implements FeignLogInterceptor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onResponse(String apiName, String url, String requestBody, Integer status, String responseBody, long interval) throws IOException {
        logger.info("apiName:{},url:{},requestBody:{},responseBody:{},interval:{}", apiName, url, requestBody, responseBody, interval);
    }
}
