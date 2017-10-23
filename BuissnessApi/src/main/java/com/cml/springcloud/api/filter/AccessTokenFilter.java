package com.cml.springcloud.api.filter;

import org.springframework.stereotype.Component;

/**
 * 登录拦截器,未登录的用户直接返回未登录数据
 * 
 * @author cml
 *
 */
@Component
public class AccessTokenFilter extends AbstractZuulFilter {

	@Override
	public Object run() {
		logger.info("AccessTokenFilter==>run");
		System.out.println("AccessTokenFilter=sys=>run");
		return null;
	}

	@Override
	public boolean shouldFilter() {
		logger.info("AccessTokenFilter==>shouldFilter");
		return true;
	}

	@Override
	public int filterOrder() {
		logger.info("AccessTokenFilter==>filterOrder");
		return FilterOrders.ORDER_HIGHLY;
	}

	@Override
	public String filterType() {
		logger.info("AccessTokenFilter==>filterType");
		return "pre";
	}

}
