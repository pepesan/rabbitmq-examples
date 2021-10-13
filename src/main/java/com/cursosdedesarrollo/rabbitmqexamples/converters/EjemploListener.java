package com.cursosdedesarrollo.rabbitmqexamples.converters;

import com.cursosdedesarrollo.rabbitmqexamples.RabbitmqExamplesApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EjemploListener {

    private static final Logger log = LoggerFactory.getLogger(EjemploListener.class);
    /*
    @RabbitListener(queues = RabbitmqExamplesApplication.DEFAULT_PARSING_QUEUE)
    public void consumeDefaultMessage(final Message message) {
        log.info("Received message, tip is: {}", message.toString());
    }
     */
}
