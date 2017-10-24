package com.cml.springcloud.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cml.springcloud.auth.AccessTokenAuthManager;
import com.cml.springcloud.model.AuthModel;

@Controller
@RequestMapping("/auth")
public class AuthController {

	protected static Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AccessTokenAuthManager authTokenManager;

	@Autowired
	private DiscoveryClient client;

	@ResponseBody
	@RequestMapping("/info")
	public Object info() {
		return client.getServices();
	}

	/**
	 * 解密信息
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping("/decodeToken")
	@ResponseBody
	public AuthModel decodeToken(String token) {
		AuthModel authModel = new AuthModel();
		authModel.setStatus(HttpServletResponse.SC_OK);
		try {
			authModel.setToken(authTokenManager.parseToken(token));
		} catch (Exception e) {
			logger.warn("decodeTokenFail", e);
			authModel.setErrMsg("invalid token!!!");
			authModel.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return authModel;
	}

	/**
	 * 生成加密信息
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/encodeToken")
	@ResponseBody()
	public AuthModel encodeToken(String token) throws Exception {
		AuthModel authModel = new AuthModel();
		authModel.setStatus(HttpServletResponse.SC_OK);
		authModel.setToken(authTokenManager.generateToken(token));
		return authModel;
	}
}
