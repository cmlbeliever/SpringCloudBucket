package com.cml.springcloud.controller;

import com.cml.springcloud.log.LogPointer;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cml.springcloud.api.OrderApi;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class DemoController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String sererName;

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private OrderApi orderApi;


    @Qualifier("elkTopic")
    @Autowired
    private MessageChannel messageChannel;

    @RequestMapping("/elk")
    @ResponseBody
    public Map sendELK(@RequestBody Map param) {
        Map extra = new HashMap();
        extra.put("request", "request" + System.currentTimeMillis());
        extra.put("response", "response" + System.currentTimeMillis());

        Map ext = new HashMap();
        ext.put("arg1", "arg1");
        ext.put("arg2", "arg2");
        extra.put("ext", ext);

        String order = orderApi.getOrder("testUser");
        ext.put("order", order);

        LogPointer.addPoint("order", order);

        boolean sendResult = messageChannel.send(MessageBuilder.withPayload(new Gson().toJson(extra)).build());
        logger.info("===============>sendResult:" + sendResult);
        logger.info("===============>message:" + MessageBuilder.withPayload(new Gson().toJson(extra)).build().getPayload());
        return extra;
    }

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

    @RequestMapping("/retryListener")
    @ResponseBody
    public String testRetryListener() {
        try {
            return orderApi.testRetry();
        } catch (Exception e) {
            logger.error("", e);
            return "error:" + e.getMessage();
        }
    }
}
