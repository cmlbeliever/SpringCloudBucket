package com.cml.springcloud.kafka;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Stream {
    String channel = "elkTopic";

    @Output(channel)
    MessageChannel elkTopic();
}
