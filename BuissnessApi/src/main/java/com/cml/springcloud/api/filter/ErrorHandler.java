package com.cml.springcloud.api.filter;

public interface ErrorHandler {
	int getResponseCode();

	String getResponseBody(Throwable e);
}
