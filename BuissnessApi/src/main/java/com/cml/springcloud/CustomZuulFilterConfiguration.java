package com.cml.springcloud;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.cml.springcloud.api.filter.AccessTokenFilter;
import com.cml.springcloud.api.filter.ErrorFilter;
import com.cml.springcloud.api.filter.ResponseHandler;
import com.cml.springcloud.api.filter.GetRequestAccessTokenFilter;
import com.cml.springcloud.api.filter.ResponseFilter;
import com.google.gson.Gson;

@Configuration
public class CustomZuulFilterConfiguration {

	@Bean
	public AccessTokenFilter accessTokenFilter() {
		return new AccessTokenFilter();
	}

	@Bean
	public ErrorFilter errorFilter(CustomErrorHandler errorHandler) {
		ErrorFilter errorFilter = new ErrorFilter();
		errorFilter.setErrorHandler(errorHandler);
		return errorFilter;
	}

	@Bean
	public GetRequestAccessTokenFilter getRequestAccessTokenFilter() {
		return new GetRequestAccessTokenFilter();
	}

	@Bean
	public ResponseFilter responseFilter() {
		return new ResponseFilter();
	}

	/**
	 * zuul 错误处理
	 * 
	 * @author cml
	 *
	 */
	@Component
	public class CustomErrorHandler implements ResponseHandler {

		@Value("${system.config.error.message}")
		private String errorMessage;

		@Override
		public int getResponseCode() {
			return HttpServletResponse.SC_OK;
		}

		@Override
		public String getResponseBody(String originMessage, Throwable e) {
			Gson gson = new Gson();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			result.put("message", errorMessage);
			System.out.println("====>" + errorMessage);
			return gson.toJson(result);
		}
	}

}
