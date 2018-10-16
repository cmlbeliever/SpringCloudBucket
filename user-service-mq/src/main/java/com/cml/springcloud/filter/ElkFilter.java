package com.cml.springcloud.filter;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = "/*")
public class ElkFilter extends BaseWebFilter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void onRequestFinished(RequestLog requestLog) {
        logger.info(new Gson().toJson(requestLog));
    }
}
