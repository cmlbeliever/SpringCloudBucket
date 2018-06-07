package com.cml.springcloud.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Stream {
    String channel = "myChannel";

    @Input(channel)
    SubscribableChannel subscribe();

    @Output(channel)
    MessageChannel send();
}
