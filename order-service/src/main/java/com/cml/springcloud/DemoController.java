package com.cml.springcloud;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cml.springcloud.api.UserApi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

@RefreshScope
@Controller
@RequestMapping("/")
public class DemoController {

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String sererName;

    @Value("${system.order.serverName}")
    private String serverName;

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private UserApi userApi;

    @Autowired
    private EurekaAutoServiceRegistration eurekaAutoServiceRegistration;

    @ResponseBody
    @RequestMapping("/test")
    public String test(String user) {
        return "port:" + port + ",serverName:" + sererName + ",getUser info ==>" + userApi.getUser(user);
    }

    private AtomicLong counter = new AtomicLong(1);

    /**
     * 每次請求計數器加一，当请求的次数不为3的倍数时，休眠2.1s返回
     *
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @RequestMapping("/testRetry")
    public String testRetry() throws InterruptedException {
        if (counter.incrementAndGet() % 3 != 0) {
            Thread.sleep(2100);
        }
        return "port:" + port;
    }

    @ResponseBody
    @RequestMapping("/infoTest")
    public Object info() {
        return client.getServices() + ",serverName:" + serverName;
    }

    @ResponseBody
    @RequestMapping("/info2")
    public Object info2() {
        return client.getServices();
    }

    @ResponseBody
    @RequestMapping("/hello")
    public String sayHello(String req) {
        return "req:" + req + ",from : port:" + port + ",serverName:" + sererName;
    }

    @RequestMapping("/order")
    @ResponseBody
    public String getOrder(String user) throws InterruptedException {
        return "Get user[ " + user + "] order from port:" + port + ",serverName:" + sererName;
    }

    @ResponseBody
    @GetMapping("/eurekaUnRegister")
    public String shutDown() {
        eurekaClient.shutdown();
        return "eurekaUnRegistering";
    }
}
