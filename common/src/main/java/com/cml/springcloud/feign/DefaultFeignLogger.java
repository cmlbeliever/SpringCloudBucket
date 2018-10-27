package com.cml.springcloud.feign;

import com.cml.springcloud.interceptor.RemoteLogInterceptor;
import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;

import java.io.IOException;

import static feign.Util.UTF_8;
import static feign.Util.decodeOrDefault;

/**
 * @Auther: cml
 * @Date: 2018-09-29 11:10
 * @Description:
 */
public class DefaultFeignLogger extends Logger {

    private RemoteLogInterceptor logInterceptor;

    public DefaultFeignLogger(RemoteLogInterceptor canaryLogInterceptor) {
        this.logInterceptor = canaryLogInterceptor;
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {

        String body = "";

        int status = response.status();
        if (response.body() != null && !(status == 204 || status == 205)) {
            // HTTP 204 No Content "...response MUST NOT include a message-body"
            // HTTP 205 Reset Content "...response MUST NOT include an entity"
            byte[] bodyData = Util.toByteArray(response.body().asInputStream());
            body = decodeOrDefault(bodyData, UTF_8, "Binary data");
            response = response.toBuilder().body(bodyData).build();
        }
        String requestBody = response.request().charset() != null ? new String(response.request().body(), response.request().charset()) : null;
        logInterceptor.onResponse(configKey.split("#")[0], response.request().url(), requestBody, response.status(), body, elapsedTime);
        return response;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {

    }
}
