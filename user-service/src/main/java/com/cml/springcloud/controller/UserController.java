package com.cml.springcloud.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cml.springcloud.model.LoginResultVO;
import com.cml.springcloud.model.LoginVO;
import com.cml.springcloud.model.UserResultVO;

@Controller
@RequestMapping("/")
public class UserController {
	protected static Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * 用户登录接口，只需要用户名和密码相同即可
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@PostMapping("/login")
	public LoginResultVO login(LoginVO user) {
		logger.info("用户登录：username:" + user.getUsername());
		LoginResultVO result = new LoginResultVO();
		if (StringUtils.isNotBlank(user.getUsername()) && StringUtils.equals(user.getUsername(), user.getPassword())) {
			String token = UUID.randomUUID().toString();
			result.setToken(token);
			result.setStatus(HttpServletResponse.SC_OK);
		} else {
			result.setErrMsg("用户名或密码错误");
			result.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return result;
	}

	@ResponseBody
	@PostMapping("/getUserInfoByToken")
	public UserResultVO getUserInfoByToken(String token) {
		logger.info("获取用户信息：token:" + token);
		UserResultVO result = new UserResultVO();
		if (StringUtils.isNotBlank(token)) {
			// 查询数据库获取用户信息
			result.setUsername("username");
			result.setNickname("nickname");
			result.setStatus(HttpServletResponse.SC_OK);
		} else {
			result.setErrMsg("invalid token!");
			result.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return result;
	}

}
