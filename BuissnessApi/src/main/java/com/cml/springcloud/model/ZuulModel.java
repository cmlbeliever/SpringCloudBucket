package com.cml.springcloud.model;

public class ZuulModel {
	private String userInfo;
	private String orderInfo;
	private String systemMsg;

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getSystemMsg() {
		return systemMsg;
	}

	public void setSystemMsg(String systemMsg) {
		this.systemMsg = systemMsg;
	}

}
