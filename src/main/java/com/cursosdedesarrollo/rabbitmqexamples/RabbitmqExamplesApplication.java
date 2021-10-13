package com.cursosdedesarrollo.rabbitmqexamples;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqExamplesApplication {
    public static final String topicExchangeName = "mi-canal";
    public static final String queueName = "unacola";

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqExamplesApplication.class, args);
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    @RabbitListener(queues = queueName)
    public void listen(String in) {
        System.out.println(in);
    }




}
