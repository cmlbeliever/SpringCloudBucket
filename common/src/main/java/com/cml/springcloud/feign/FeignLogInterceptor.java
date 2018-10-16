package com.cml.springcloud.feign;

import java.io.IOException;

/**
 * @Auther: cml
 * @Date: 2018-09-29 11:09
 * @Description:
 */
public interface FeignLogInterceptor {
    void onResponse(String apiName, String url, String requestBody, Integer status, String responseBody, long interval) throws IOException;
}
