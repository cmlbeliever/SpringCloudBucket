package com.cml.springcloud.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUtil {

    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationUtil.applicationContext = applicationContext;
    }

    /**
     * 获取环境
     * <p>
     * 获取不到环境配置的情况下，默认是DEV环境
     *
     * @return env
     */
    public static String getEnv() {
        String env = System.getProperty("spring.profiles.active");
        return StringUtils.defaultIfBlank(env, "local").toLowerCase();
    }

    public static String getApplicationName() {
        String name = applicationContext.getEnvironment().getProperty("spring.application.name");
        return StringUtils.defaultIfBlank(name, "none").toLowerCase();
    }
}