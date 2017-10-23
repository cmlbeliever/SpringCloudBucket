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
		try {
			RequestContext context = getCurrentContext();
			logger.info("====requestMethod:" + context.getRequest().getMethod());
			logger.info("====header:" + context.getRequest().getHeaderNames());

			// context.set("requestEntity", new
			// ByteArrayInputStream(reqBodyBytes));
			try {
				logger.info("ori url:" + context.getRouteHost());
				URL url = UriComponentsBuilder.fromUri(context.getRouteHost().toURI()).queryParam("user", "modifydUser")
						.build().toUri().toURL();
				logger.info("url:" + url);
				context.setRouteHost(url);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			rethrowRuntimeException(e);
		}
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
		return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
	}

	@Override
	public String filterType() {
		logger.info("AccessTokenFilter==>filterType");
		return FilterConstants.PRE_TYPE;
	}

}
