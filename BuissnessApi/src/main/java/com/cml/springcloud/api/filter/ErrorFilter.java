package com.cml.springcloud.api.filter;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.netflix.zuul.context.RequestContext;

public class ErrorFilter extends AbstractZuulFilter {

	@Override
	public boolean shouldFilter() {
		logger.info("ErrorFilter==>shouldFilter");
		return false;
	}

	@Override
	public Object run() {
		logger.info("ErrorFilter==>run");
		try {
			RequestContext context = getCurrentContext();
			// InputStream stream = context.getResponseDataStream();
			// String body = StreamUtils.copyToString(stream,
			// Charset.forName("UTF-8"));
			context.setResponseBody("some error occured!");
		} catch (Exception e) {
			rethrowRuntimeException(e);
		}
		return null;
	}

	@Override
	public String filterType() {
		logger.info("ErrorFilter==>filterType");
		return "error";
	}

	@Override
	public int filterOrder() {
		logger.info("ErrorFilter==>filterOrder");
		return FilterOrders.ORDER_HIGHLY;
	}

}
