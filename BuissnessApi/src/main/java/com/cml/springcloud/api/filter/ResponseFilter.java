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
		logger.info("ResponseFilter==>shouldFilter");
		return false;
	}

	@Override
	public Object run() {
		logger.info("ResponseFilter==>run");
		return null;
	}

	@Override
	public String filterType() {
		logger.info("ResponseFilter==>filterType");
		return "post";
	}

	@Override
	public int filterOrder() {
		logger.info("ResponseFilter==>filterOrder");
		return FilterOrders.ORDER_LOWEST;
	}

}
