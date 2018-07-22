package com.cml.springcloud.controller;

import java.util.UUID;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.DiscoveryManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cml.springcloud.constant.ApiServiceContanst;
import com.cml.springcloud.model.request.LoginVO;
import com.cml.springcloud.model.result.LoginResult;
import com.cml.springcloud.model.result.UserResult;

@Controller
@RequestMapping("/")
public class UserController {
    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/shutdown")
    public void shutDown() {
        DiscoveryManager.getInstance().shutdownComponent();
    }

    /**
     * 用户登录接口，只需要用户名和密码相同即可
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public LoginResult login(LoginVO user) {
        logger.info("用户登录：username:" + user.getUsername());
        LoginResult result = new LoginResult();
        if (StringUtils.isNotBlank(user.getUsername()) && StringUtils.equals(user.getUsername(), user.getPassword())) {
            String token = UUID.randomUUID().toString();
            result.setToken(token);
            result.setStatus(ApiServiceContanst.ResultCode.RESULT_OK);
        } else {
            result.setErrMsg("用户名或密码错误");
            result.setStatus(ApiServiceContanst.ResultCode.RESULT_FAIL);
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/getUserInfoByToken")
    public UserResult getUserInfoByToken(String token) {
        logger.info("获取用户信息：token:" + token);
        UserResult result = new UserResult();
        if (StringUtils.isNotBlank(token)) {
            // 查询数据库获取用户信息
            result.setUsername("username");
            result.setNickname("nickname");
            result.setStatus(ApiServiceContanst.ResultCode.RESULT_OK);
        } else {
            result.setErrMsg("invalid token!");
            result.setStatus(ApiServiceContanst.ResultCode.RESULT_FAIL);
        }
        return result;
    }

}
