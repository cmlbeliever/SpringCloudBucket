package com.cml.springcloud.log;

import com.cml.springcloud.feign.FeignLogInterceptor;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ElkFeignLogInterceptor implements FeignLogInterceptor {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Qualifier("elkTopic")
    @Autowired
    private MessageChannel messageChannel;

    @Override
    public void onResponse(String apiName, String url, String requestBody, Integer status, String responseBody, long interval) throws IOException {
        logger.info("apiName:{},url:{},requestBody:{},responseBody:{},interval:{}", apiName, url, requestBody, responseBody, interval);

        RequestLog requestLog = new RequestLog();
        requestLog.setResponse(responseBody);
        requestLog.setRequestBody(requestBody);
        requestLog.setUrl(url);
        requestLog.setInterval(interval);
        requestLog.setName(apiName);

        KafkaLog<RequestLog> requestLogKafkaLog = new KafkaLog<>();
        requestLogKafkaLog.setData(requestLog);
        requestLogKafkaLog.setType("Feignf");
        requestLogKafkaLog.setIndex("web-request");
        requestLogKafkaLog.setExtra(LogPointer.getPoints());

        boolean sendResult = messageChannel.send(MessageBuilder.withPayload(requestLogKafkaLog).build());
        if (!sendResult) {
            logger.error("发送kafka消息失败：{}", new Gson().toJson(requestLogKafkaLog));
        }

        logger.info(new Gson().toJson(requestLogKafkaLog));
        logger.info("points:" + new Gson().toJson(LogPointer.getPoints()));
    }
}
