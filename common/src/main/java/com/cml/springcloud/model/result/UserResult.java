package com.cml.springcloud.model.result;

public class UserResult extends BaseResult {
	private String username;
	private String nickname;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
