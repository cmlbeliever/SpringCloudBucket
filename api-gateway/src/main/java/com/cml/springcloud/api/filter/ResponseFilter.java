package com.cml.springcloud.api.filter;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
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
		RequestContext context = getCurrentContext();
		// 有错误的返回不处理，其他请求都处理
		return null == context.get(ErrorFilter.KEY_ERROR);
	}

	@Override
	public Object run() {

		try {
			RequestContext context = getCurrentContext();

			InputStream stream = context.getResponseDataStream();
			String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
			context.setResponseBody("Modified via setResponseBody(): " + body);
			context.setResponseStatusCode(200);
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
		return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
	}

}
