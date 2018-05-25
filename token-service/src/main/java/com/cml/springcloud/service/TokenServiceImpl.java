package com.cml.springcloud.service;

import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenServiceImpl implements TokenService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private TokenGeneratorService tokenGeneratorService;

    @Value("${token.sentinel.key}")
    private String redisTokenKey;

    @Value("${token.sentinel.count}")
    private int tokenMaxCount;
    @Value("${token.sentinel.threshold}")
    private int tokenThresholdCount;


    @Override
    public String pop() {
        return (String) redissonClient.getList(redisTokenKey).remove(0);
    }

    @Override
    public void fillTokenList() {
        RList rList = redissonClient.getList(redisTokenKey);
        int realCount = rList.size();

        logger.info("{}队列中数量为：{}，需要:{}", redisTokenKey, realCount, tokenMaxCount);

        if (realCount < tokenThresholdCount) {
            long start = System.currentTimeMillis();
            int needCount = tokenMaxCount - realCount;
            for (int i = 0; i < needCount; i++) {
                rList.add(tokenGeneratorService.generate());
            }
            logger.info("本次生成token数量：{}耗时:{}ms", needCount, System.currentTimeMillis() - start);
        }
    }
}
