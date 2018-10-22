package com.cml.springcloud.filter;

import com.cml.springcloud.log.KafkaLog;
import com.cml.springcloud.log.LogPointer;
import com.cml.springcloud.log.RequestLog;
import com.cml.springcloud.util.ApplicationUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.servlet.annotation.WebFilter;
import java.net.InetAddress;
import java.net.UnknownHostException;

@WebFilter(urlPatterns = "/*")
public class ElkFilter extends BaseWebFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Qualifier("elkTopic")
    @Autowired
    private MessageChannel messageChannel;

    @Override
    protected void onRequestFinished(RequestLog requestLog) {

        KafkaLog<RequestLog> requestLogKafkaLog = new KafkaLog<>();
        requestLogKafkaLog.setData(requestLog);
        requestLogKafkaLog.setType("WebRequest");
        requestLogKafkaLog.setIndex("web-request");
        requestLogKafkaLog.setExtra(LogPointer.getPoints());
        requestLogKafkaLog.setEnv(ApplicationUtil.getEnv());
        requestLogKafkaLog.setServiceName(ApplicationUtil.getApplicationName());
        requestLogKafkaLog.setTraceId(LogPointer.getTraceId());
        try {
            requestLogKafkaLog.setIp(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        boolean sendResult = messageChannel.send(MessageBuilder.withPayload(requestLogKafkaLog).build());
        if (!sendResult) {
            logger.error("发送kafka消息失败：{}", new Gson().toJson(requestLogKafkaLog));
        }

        logger.info(new Gson().toJson(requestLogKafkaLog));
        logger.info("points:" + new Gson().toJson(LogPointer.getPoints()));
    }
}
