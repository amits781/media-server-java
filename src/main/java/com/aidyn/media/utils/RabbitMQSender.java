package com.aidyn.media.utils;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${media.server.rabbitmq.exchange}")
    private String exchange;

    @Value("${media.server.rabbitmq.routingkey}")
    private String routingkey;

    public void send(String movieName) {
	amqpTemplate.convertAndSend(exchange, routingkey, movieName);
	log.info("Sent in Queue for processing = " + movieName);
    }
}