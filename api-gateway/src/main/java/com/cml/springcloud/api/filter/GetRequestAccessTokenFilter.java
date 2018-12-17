package com.cml.springcloud.api.filter;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.zuul.context.RequestContext;

/**
 * GET方式拦截器,未登录的用户直接返回未登录数据
 * 
 * @author cml
 *
 */
public class GetRequestAccessTokenFilter extends AbstractZuulFilter {

	@Override
	public Object run() {
		logger.info("GetRequestAccessTokenFilter==>run");
		RequestContext context = getCurrentContext();
		logger.info("====requestMethod:" + context.getRequest().getMethod());
		logger.info("====header:" + context.getRequest().getHeaderNames());

		String url = (String) context.get(FilterConstants.REQUEST_URI_KEY);

		logger.info("ori url:" + url + ",serviceId:" + context.get("serviceId"));

//		if (true) {
//			rethrowRuntimeException(new RuntimeException("主动跑错"));
//		}
		// URL url =
		// UriComponentsBuilder.fromUriString(context.getRequest().getRequestURL().toString()).queryParam("user",
		// "modifydUser").build()
		// context.set(FilterConstants.REQUEST_URI_KEY, url + "?user=test");
		return null;
	}

	@Override
	public boolean shouldFilter() {
		logger.info("GetRequestAccessTokenFilter==>shouldFilter");
		return StringUtils.equalsIgnoreCase(RequestContext.getCurrentContext().getRequest().getMethod(), "get");
	}

	@Override
	public int filterOrder() {
		logger.info("GetRequestAccessTokenFilter==>filterOrder");
		return FilterConstants.RIBBON_ROUTING_FILTER_ORDER - 1;
	}

	@Override
	public String filterType() {
		logger.info("AccessTokenFilter==>filterType");
		return FilterConstants.ROUTE_TYPE;
	}

}
