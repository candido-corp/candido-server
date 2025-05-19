package com.candido.server.event.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KeycloakEventConsumer {
    @KafkaListener(topics = "keycloak.user-events", groupId = "candido-consumer")
    public void handleEvent(String message) {
        System.out.println("Received event: " + message);
    }
}

