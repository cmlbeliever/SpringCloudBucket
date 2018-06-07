package com.cml.springcloud;

import com.cml.springcloud.kafka.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.MessageBuilder;

//@EnableFeignClients
//@EnableDiscoveryClient
@SpringBootApplication()
@EnableBinding({Stream.class, Sink.class})
public class KafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Autowired
    private Stream stream;

    @Bean
    public ApplicationRunner runner() {
        return (args) -> {
            stream.send().send(MessageBuilder.withPayload("2134567890").build());
        };
    }

    @StreamListener(Stream.channel)
    public void handle(String message) {
        System.out.println("receiveMessage==>" + message);
    }
}
