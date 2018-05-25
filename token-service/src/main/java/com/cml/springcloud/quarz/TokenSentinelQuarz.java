package com.cml.springcloud.quarz;

import com.cml.springcloud.service.TokenGeneratorService;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 保障token数量
 */
@Component
public class TokenSentinelQuarz {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedissonClient redissonClient;

    @Value("${token.sentinel.key}")
    private String redisTokenKey;

    @Value("${token.sentinel.count}")
    private int tokenMaxCount;

    @Autowired
    private TokenGeneratorService tokenGeneratorService;


    @Scheduled(fixedRate = 1000)
    public void sentinelTask() {
        logger.info("==========>sentinelTask");

        RList rList = redissonClient.getList(redisTokenKey);
        int realCount = rList.size();

        logger.info("{}队列中数量为：{}，需要:{}", redisTokenKey, realCount, tokenMaxCount);

        if (realCount < tokenMaxCount) {
            long start = System.currentTimeMillis();
            int needCount = tokenMaxCount - realCount;
            for (int i = 0; i < needCount; i++) {
                rList.add(tokenGeneratorService.generate());
            }
            logger.info("本次生成token数量：{}耗时:{}ms", needCount, System.currentTimeMillis() - start);
        }

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
