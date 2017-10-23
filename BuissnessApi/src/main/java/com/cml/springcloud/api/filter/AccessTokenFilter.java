package com.cml.springcloud.api.filter;

/**
 * 登录拦截器,未登录的用户直接返回未登录数据
 * 
 * @author cml
 *
 */
public class AccessTokenFilter extends AbstractZuulFilter {

	@Override
	public Object run() {
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return FilterOrders.ORDER_HIGHLY;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
