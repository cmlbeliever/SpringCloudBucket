package com.cml.springcloud.api.filter;

/**
 * API返回数据处理接口，提供返回状态码和返回的内容
 * 
 * @author cml
 *
 */
public interface ResponseHandler {
	/**
	 * 获取返回的http状态码
	 * 
	 * @return
	 */
	int getResponseCode();

	/**
	 * 获取返回的内容
	 * 
	 * @param originMessage
	 *            默认返回的消息
	 * @param e
	 *            错误信息
	 * @return 处理后返回的信息
	 */
	String getResponseBody(String originMessage, Throwable e);
}
