package com.cml.springcloud.api.filter;

import org.springframework.stereotype.Component;

/**
 * 返回数据处理，添加自定义返回信息
 * 
 * @author cml
 *
 */
@Component
public class ResponseFilter extends AbstractZuulFilter {

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object run() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return FilterOrders.ORDER_LOWEST;
	}

}
