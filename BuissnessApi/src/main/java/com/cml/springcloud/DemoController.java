package com.cml.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cml.springcloud.api.OrderApi;
import com.cml.springcloud.api.UserApi;
import com.cml.springcloud.model.ZuulModel;

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

	@ResponseBody
	@RequestMapping("/info")
	public Object info() {
		return client.getServices();
	}

	@RequestMapping("/zuul")
	@ResponseBody()
	public ZuulModel testFeign(@RequestParam(defaultValue = "defaultUser", required = false) String user)
			throws Exception {
		ZuulModel model = new ZuulModel();
		model.setUserInfo(userApi.getUser(user));
		model.setUserInfo(orderApi.getOrder(user));
		model.setSystemMsg("port:" + port + ",serverName:" + sererName);
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
