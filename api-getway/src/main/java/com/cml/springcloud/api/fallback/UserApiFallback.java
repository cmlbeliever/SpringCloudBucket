package com.cml.springcloud.api.fallback;

import com.cml.springcloud.api.UserApi;
import org.springframework.stereotype.Component;

@Component
public class UserApiFallback implements UserApi {
    @Override
    public String getUser(String user) {
        return null;
    }

    @Override
    public String getUser1(String user) {
        return "fallback=>";
    }
}
