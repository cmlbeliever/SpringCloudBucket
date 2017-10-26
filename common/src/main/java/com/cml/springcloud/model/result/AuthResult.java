package com.cml.springcloud.model.result;

public class AuthResult extends BaseResult {
	private String token;
	private String errMsg;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
