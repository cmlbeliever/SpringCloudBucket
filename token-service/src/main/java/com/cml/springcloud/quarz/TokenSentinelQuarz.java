package com.cml.springcloud.quarz;

import com.cml.springcloud.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 保障token数量
 */
@ConditionalOnProperty(name = "sentinel.enable", havingValue = "true")
@Component
public class TokenSentinelQuarz {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TokenService tokenService;

    @Scheduled(fixedRate = 1000)
    public void sentinelTask() {
        tokenService.fillTokenList();
    }

//    @Scheduled(fixedRate = 100)
//    public void sentinelTask2() {
//        logger.info("==========>sentinelTask2");
//        RList rList = redissonClient.getList(redisTokenKey);
//        for (int i = 0; i < 1000; i++) {
//            String token = (String) rList.remove(0);
//        }
//    }
}
