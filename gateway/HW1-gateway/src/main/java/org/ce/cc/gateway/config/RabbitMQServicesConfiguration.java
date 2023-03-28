package org.ce.cc.gateway.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQServicesConfiguration {

    @Bean
    public Queue myQueue() {
        return new Queue("myQueue", false);
    }



}
