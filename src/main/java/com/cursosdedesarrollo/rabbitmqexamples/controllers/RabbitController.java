package com.cursosdedesarrollo.rabbitmqexamples.controllers;

import com.cursosdedesarrollo.rabbitmqexamples.model.StringRequest;
import com.cursosdedesarrollo.rabbitmqexamples.model.StringResponse;
import com.google.gson.Gson;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
public class RabbitController {
    public static final String nombreCola = "micola";
    public static final String nombreCanal = "micanal";
    public static final String nombreColaCanal = "micolacanal";
    private ConnectionFactory connectionFactory = null;
    private AmqpTemplate template = null;
    @GetMapping("/echo/{msg}")
    StringResponse echo(@PathVariable String msg) {
        return new StringResponse(msg);
    }
    @GetMapping("/mandaCola/{msg}")
    StringResponse sendQueue(@PathVariable String msg) {
        // System.out.println(msg);
        // Crea la factoría
        connectionFactory = new CachingConnectionFactory(
                "localhost", 5672);
        // Crea la conexión de administración
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        // Declara la cola
        admin.declareQueue(new Queue(nombreCola, true));
        // Crea la conexión de consulta
        template = new RabbitTemplate(connectionFactory);
        // Manda el mensaje a la cola
        template.convertAndSend(nombreCola, msg);
        // Devuleve los resultados
        return new StringResponse(msg);
    }
    @GetMapping("/recibeCola")
    StringResponse receiveQueue() {
        // Crea la factoría
        connectionFactory = new CachingConnectionFactory();
        // Crea la conexión de consulta
        template = new RabbitTemplate(connectionFactory);
        String ret = null;
        try {
            ret = (String) template.receiveAndConvert(nombreCola);
        } catch (AmqpException e) {
            System.out.println("AMQP");
            e.printStackTrace();
        }

        // Devuelve los resultados
        return new StringResponse(ret);
    }
    @PostMapping("/mandaColaJson")
    StringResponse sendQueueJson(@RequestBody StringRequest mensaje) {
        // System.out.println(msg);
        // Crea la factoría
        connectionFactory = new CachingConnectionFactory(
                "localhost", 5672);
        // Crea la conexión de administración
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        // Declara la cola
        admin.declareQueue(new Queue(nombreCola, true));
        // Crea la conexión de consulta
        template = new RabbitTemplate(connectionFactory);
        // Manda el mensaje a la cola
        template.convertAndSend(nombreCola, new Gson().toJson(mensaje));
        // Devuleve los resultados
        return new StringResponse(mensaje.msg);
    }
    @GetMapping("/recibeColaJson")
    StringRequest receiveQueueJson() {
        // Crea la factoría
        connectionFactory = new CachingConnectionFactory();
        // Crea la conexión de consulta
        template = new RabbitTemplate(connectionFactory);
        StringRequest request = null;
        try {
            String ret = (String) template.receiveAndConvert(nombreCola);
            request = new Gson().fromJson(ret, StringRequest.class);
        } catch (AmqpException e) {
            System.out.println("AMQP");
            e.printStackTrace();
        }
        // Devuelve los resultados
        return request;
    }

    @GetMapping("/mandaCanal/{msg}")
    StringResponse sendTopic(@PathVariable String msg) {
        // Crea la factoría
        connectionFactory = new CachingConnectionFactory(
                "localhost",
                5672);
        // Crea la conexión de administración
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        // Declara la canal
        TopicExchange miCanal = new TopicExchange(nombreCanal);
        admin.declareExchange(miCanal);
        // Declara la cola del canal
        Queue miColaCanal = new Queue(nombreColaCanal);
        admin.declareQueue(miColaCanal);
        Binding binding = BindingBuilder
                .bind(miColaCanal)
                .to(miCanal)
                .with("foo.bar.#");
        admin.declareBinding(binding);
        // Crea la conexión de consulta
        template = new RabbitTemplate(connectionFactory);
        String routingKey = "foo.bar.#";
        // Manda el mensaje al canal
        //template.send(nombreCanal, routingKey, new Message(msg.getBytes(StandardCharsets.UTF_8)));
        template.convertAndSend(nombreCanal, routingKey, msg);
        // Devuleve los resultados
        return new StringResponse(msg);
    }
    @GetMapping("/recibeCanal")
    StringResponse receiveTopic() {
        // Crea la factoría
        connectionFactory = new CachingConnectionFactory();
        // Crea la conexión de consulta
        template = new RabbitTemplate(connectionFactory);
        String ret = null;
        try {
            //ret = new String((byte[]) Objects.requireNonNull(template.receiveAndConvert(nombreColaCanal)));
            ret = (String) template.receiveAndConvert(nombreColaCanal);
        } catch (AmqpException e) {
            System.out.println("AMQP");
            e.printStackTrace();
        }

        // Devuelve los resultados
        return new StringResponse(ret);
    }
    @GetMapping("/recibeCanalComvertido")
    StringResponse receiveTopicConverted() {
        String nombreCanal2 = "micanal2";
        // Crea la factoría
        connectionFactory = new CachingConnectionFactory();
        // Crea la conexión de consulta
        template = new RabbitTemplate(connectionFactory);
        String ret = null;
        try {
            //ret = new String((byte[]) Objects.requireNonNull(template.receiveAndConvert(nombreColaCanal)));
            ret = (String) template.receiveAndConvert(nombreColaCanal);
        } catch (AmqpException e) {
            System.out.println("AMQP");
            e.printStackTrace();
        }

        // Devuelve los resultados
        return new StringResponse(ret);
    }




}
