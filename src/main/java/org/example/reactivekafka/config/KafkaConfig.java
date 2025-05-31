package org.example.reactivekafka.config;


import org.example.reactivekafka.model.Message;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfig {
    @Bean
    public ReactiveKafkaProducerTemplate<String, Message> reactiveKafkaProducerTemplate(KafkaProperties kafkaProperties) {

        // Get all properties from application.yml
        Map<String, Object> props = kafkaProperties.buildProducerProperties();

        // Create sender options with the exact types you need
        SenderOptions<String, Message> senderOptions = SenderOptions.create(props);

        return new ReactiveKafkaProducerTemplate<>(senderOptions);
    }
}