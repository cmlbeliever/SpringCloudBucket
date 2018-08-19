package com.cml.springcloud.ribbon.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryContext;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

public class RibbonRetryListener implements RetryListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {

    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        String service = ((LoadBalancedRetryContext) context).getServiceInstance().getServiceId();
        logger.error(service + "微服务调用错误：", throwable);
    }
}
