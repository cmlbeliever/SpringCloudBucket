package com.cml.springcloud.service;

import com.cml.springcloud.util.SnowflakeIdWorker;
import org.springframework.stereotype.Component;

@Component
public class TokenGeneratorServiceImpl implements TokenGeneratorService {

    private SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1, 1);

    @Override
    public String generate() {
        return idWorker.nextId() + "";
    }
}
