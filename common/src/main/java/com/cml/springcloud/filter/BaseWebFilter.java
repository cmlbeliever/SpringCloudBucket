package com.cml.springcloud.filter;

import com.cml.springcloud.log.LogPointer;
import com.cml.springcloud.log.RequestLog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseWebFilter implements Filter {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        DefaultRequestWrapper defaultRequestWrapper = new DefaultRequestWrapper((HttpServletRequest) request);
        DefaultResponseWrapper defaultResponseWrapper = new DefaultResponseWrapper((HttpServletResponse) response);

        long startTime = System.currentTimeMillis();
        LogPointer.init();

        RequestLog requestLog = new RequestLog();
        requestLog.setRemoteIp(getIpAddress((HttpServletRequest) request));
        try {
            chain.doFilter(defaultRequestWrapper, defaultResponseWrapper);
            requestLog.setSuccessful(true);
        } catch (Exception e) {
            requestLog.setSuccessful(false);
            requestLog.setError(e);
        } finally {
            requestLog.setRequestBody(defaultRequestWrapper.getRequestBody());
            requestLog.setRequestBodyLength(defaultRequestWrapper.getContentLength());
            requestLog.setUrl(defaultRequestWrapper.getRequestURL().toString());
            requestLog.setName(defaultRequestWrapper.getRequestURI());
            requestLog.setInterval(System.currentTimeMillis() - startTime);

            String responseBody = defaultResponseWrapper.getResponseBody();
            requestLog.setResponse(responseBody);
            requestLog.setRequestBodyLength(StringUtils.length(responseBody));

            onRequestFinished(requestLog);

            LogPointer.clear();
        }

    }

    protected abstract void onRequestFinished(RequestLog requestLog);

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            return ip.split(",")[0];
        } else {
            return ip;
        }
    }

    @Override
    public void destroy() {

    }
}
