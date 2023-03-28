package org.ce.cc.gateway.service;

public interface RabbitMQSenderService {
    void send(long uploadId);
}
