package org.ce.cc.notifier.config;

import lombok.RequiredArgsConstructor;
import org.ce.cc.core.repository.JobRepository;
import org.ce.cc.core.repository.ResultRepository;
import org.ce.cc.core.repository.UploadRepository;
import org.ce.cc.notifier.service.NotifierService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQServicesConfiguration {
    @Autowired
    private final NotifierService notifierService;


    @Bean
    public Queue myQueue() {
        return new Queue("myQueue", false);
    }

    @RabbitListener(queues = "myQueue")
    public void listen(long uploadId) {
        System.out.println("Notifier: UploadId read from myQueue : " + uploadId);
        notifierService.start(uploadId);


    }

}
