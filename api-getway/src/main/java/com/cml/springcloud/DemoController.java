package com.cml.springcloud;

import com.cml.springcloud.service.HystrixTestService;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cml.springcloud.api.OrderApi;
import com.cml.springcloud.api.UserApi;
import com.cml.springcloud.model.result.ZuulResult;
import com.cml.springcloud.service.RetryableService;

@Controller
@RequestMapping("/biz")
public class DemoController {

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String sererName;

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private UserApi userApi;
    @Autowired
    private OrderApi orderApi;

    @Autowired
    private RetryableService retryService;

    @Autowired
    private HystrixTestService hystrixTestService;

    @ResponseBody
    @RequestMapping("/retryTest")
    public String retryTest(int type) {
        return retryService.testRetry(type);
    }

    @ResponseBody
    @RequestMapping("/info")
    public Object info() {
        return client.getServices();
    }

    @ResponseBody
    @RequestMapping("/errorController")
    public String error() {
        return "error";
    }

    @Hystrix
    @RequestMapping("/zuul")
    @ResponseBody()
    public ZuulResult testFeign(@RequestParam(defaultValue = "defaultUser", required = false) String user) throws Exception {
        ZuulResult model = new ZuulResult();
        model.setUserInfo(userApi.getUser(user));
        model.setOrderInfo(orderApi.getOrder(user));
        model.setSystemMsg("port:" + port + ",serverName:" + sererName);
        model.setErrMsg(hystrixTestService.getTestValue("==="));
        return model;
    }
    // @RequestMapping("/zuul")
    // @ResponseBody()
    // public JSONObject testFeign(@RequestParam(defaultValue = "defaultUser",
    // required = false) String user)
    // throws Exception {
    // JSONObject result = new JSONObject();
    // result.put("userInfo", userApi.getUser(user));
    // result.put("orderInfo", orderApi.getOrder(user));
    // result.put("zuulServerInfo", "port:" + port + ",serverName:" +
    // sererName);
    // return result;
    // }
}
