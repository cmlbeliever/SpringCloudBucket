package com.cml.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cml.springcloud.api.OrderApi;

@Controller
@RequestMapping("/")
public class DemoController {
    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String sererName;

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private OrderApi orderApi;

    @ResponseBody
    @RequestMapping("/info")
    public Object info() {
        return client.getServices();
    }

    @ResponseBody
    @RequestMapping("/getUser")
    public String getUser(String user) {
        return "get userInfo user:[" + user + "],from : port:" + port + ",serverName:" + sererName;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(String user) {
        return "Get userInfo[ " + user + "]  from port:" + port + ",serverName:" + sererName + "\n has order:"
                + orderApi.getOrder("u" + user);
    }
}
