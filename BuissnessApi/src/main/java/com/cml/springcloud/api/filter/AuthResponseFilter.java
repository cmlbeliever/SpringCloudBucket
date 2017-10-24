package com.cml.springcloud.api.filter;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.StreamUtils;

import com.cml.springcloud.api.AuthApi;
import com.cml.springcloud.model.AuthModel;
import com.google.gson.Gson;
import com.netflix.zuul.context.RequestContext;

/**
 * 登录验证后数据加密处理
 * 
 * @author cml
 *
 */
public class AuthResponseFilter extends AbstractZuulFilter {

	private static final String RESPONSE_KEY_TOKEN = "token";
	@Value("${system.config.authFilter.authUrl}")
	private String authUrl;
	@Value("${system.config.authFilter.tokenKey}")
	private String tokenKey = RESPONSE_KEY_TOKEN;

	@Autowired
	private AuthApi authApi;

	@Override
	public boolean shouldFilter() {
		RequestContext context = getCurrentContext();
		return StringUtils.equals(context.getRequest().getRequestURI().toString(), authUrl);
	}

	@Override
	public Object run() {

		try {
			RequestContext context = getCurrentContext();

			InputStream stream = context.getResponseDataStream();
			String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));

			if (StringUtils.isNotBlank(body)) {
				Gson gson = new Gson();
				@SuppressWarnings("unchecked")
				Map<String, String> result = gson.fromJson(body, Map.class);
				if (StringUtils.isNotBlank(result.get(tokenKey))) {
					AuthModel authResult = authApi.encodeToken(result.get(tokenKey));
					if (authResult.getStatus() != HttpServletResponse.SC_OK) {
						throw new IllegalArgumentException(authResult.getErrMsg());
					}
					String accessToken = authResult.getToken();
					result.put(tokenKey, accessToken);
				}
				body = gson.toJson(result);
			}
			context.setResponseBody(body);
		} catch (IOException e) {
			rethrowRuntimeException(e);
		}
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.POST_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 2;
	}

}
