package com.cml.springcloud.api.filter;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.netflix.zuul.context.RequestContext;

/**
 * 返回数据处理，添加自定义返回信息
 * 
 * @author cml
 *
 */
public class ResponseFilter extends AbstractZuulFilter {

	@Override
	public boolean shouldFilter() {
		logger.info("ResponseFilter==>shouldFilter");
		return true;
	}

	@Override
	public Object run() {

		try {
			RequestContext context = getCurrentContext();
			logger.info("ResponseFilter==>run" + ",ex:" + context.getThrowable());

			if (null != context.getThrowable()) {
				context.setResponseBody("Modified via setResponseBody(): error o llllll");
				context.setResponseStatusCode(200);
				context.setSendZuulResponse(false);
				logger.info("ResponseFilter==> end !!!!");
				return null;
			}

			InputStream stream = context.getResponseDataStream();
			String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
			context.setResponseBody("Modified via setResponseBody(): " + body);
			context.setResponseStatusCode(200);
			// SendResponseFilter
		} catch (IOException e) {
			logger.error("response", e);
			rethrowRuntimeException(e);
		}
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
		return Integer.MAX_VALUE;
	}

}
