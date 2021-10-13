package com.cursosdedesarrollo.rabbitmqexamples;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class AMQPIntegration {
    /*
    @Bean
    public IntegrationFlow amqpInbound(ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory, RabbitmqExamplesApplication.DEFAULT_PARSING_QUEUE))
                .handle(m -> System.out.println(m.getPayload()))
                .get();
    }
    @Bean
    public MessageChannel amqpOutboundChannel() {
        return new DirectChannel();
    }
    @Bean
    public IntegrationFlow amqpOutbound(AmqpTemplate amqpTemplate,
                                        MessageChannel amqpOutboundChannel) {
        return IntegrationFlows.from(amqpOutboundChannel)
                .handle(Amqp.outboundAdapter(amqpTemplate)
                        .exchangeName("micanal")
                        .routingKey("foo.bar.#")) // default exchange - route to queue 'micola'
                .get();
    }
    */
}
