package org.example.reactivekafka.service;

import lombok.RequiredArgsConstructor;
import org.example.reactivekafka.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final ReactiveKafkaProducerTemplate<String, Message> reactiveKafkaProducerTemplate;

    @Value("${configuration.kafka.topic}")
    private String topic;

    public Mono<Void> sendMessage(Message message) {
        return reactiveKafkaProducerTemplate.send(topic, message).then();
    }

}