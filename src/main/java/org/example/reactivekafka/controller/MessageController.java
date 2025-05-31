package org.example.reactivekafka.controller;

import lombok.RequiredArgsConstructor;
import org.example.reactivekafka.model.Message;
import org.example.reactivekafka.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public Mono<ResponseEntity<String>> sendMessage(@RequestBody Mono<Message> messageMono) {
        return messageMono
                .flatMap(message -> {
                    message.setId(UUID.randomUUID().toString());
                    message.setTimestamp(Instant.now().toEpochMilli());
                    return messageService.sendMessage(message);
                })
                .then(Mono.just(ResponseEntity.ok("Message sent successfully")))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body("Error: " + e.getMessage())));
    }
}