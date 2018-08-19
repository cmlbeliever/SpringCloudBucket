package com.cml.springcloud.ribbon;


import com.cml.springcloud.ribbon.listener.RibbonRetryListener;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.retry.RetryListener;

public class DefaultRibbonLoadBalanceRetryFactory extends RibbonLoadBalancedRetryFactory {
    public DefaultRibbonLoadBalanceRetryFactory(SpringClientFactory clientFactory) {
        super(clientFactory);
    }

    @Override
    public org.springframework.retry.RetryListener[] createRetryListeners(String service) {
        return new RetryListener[]{new RibbonRetryListener()};
    }
}
