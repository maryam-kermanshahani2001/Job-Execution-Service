package org.ce.cc.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import org.ce.cc.gateway.service.RabbitMQSenderService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQSenderServiceImpl implements RabbitMQSenderService {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;
//    private final RabbitTemplate template;

    //    private Queue queue;
    /*@Bean
    public ApplicationRunner runner(RabbitTemplate template) {
        return args -> {
            template.convertAndSend("myQueue", "Hello, world!");
        };
    }*/
    public void send(long uploadId) {

//        String message = "hello world";
//        template.convertAndSend(queue.getName(), message);
        template.convertAndSend(queue.getName(), uploadId);

    }

}
