package com.cml.springcloud.api.filter;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.context.RequestContext;

public class ErrorFilter extends AbstractZuulFilter {

	@Override
	public boolean shouldFilter() {
		logger.info("ErrorFilter111==>shouldFilter");
		return true;
	}

	@Override
	public Object run() {

		try {
			RequestContext context = getCurrentContext();
			logger.info("ErrorFilter111==>run==>" + context.getThrowable().getMessage());
			// InputStream stream = context.getResponseDataStream();
			// String body = StreamUtils.copyToString(stream,
			// Charset.forName("UTF-8"));
			context.setSendZuulResponse(false);
			context.setResponseStatusCode(200);
			context.setResponseBody("some error occured!");
		
			// SendErrorFilter
		} catch (Exception e) {
			rethrowRuntimeException(e);
		}
		return null;
	}

	@Override
	public String filterType() {
		logger.info("ErrorFilter111==>filterType");
		return "error";
	}

	@Override
	public int filterOrder() {
		logger.info("ErrorFilter==>filterOrder");
		return FilterConstants.SEND_ERROR_FILTER_ORDER - 1;
	}

}
