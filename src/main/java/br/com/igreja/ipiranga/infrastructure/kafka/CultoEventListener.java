package br.com.igreja.ipiranga.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Listener de Eventos do Kafka para o contexto de Culto.
 * Camada: Infrastructure
 * 
 * Escuta o tópico "culto-updates" e encaminha as notificações para os
 * clientes conectados via WebSocket (STOMP), permitindo atualizações em tempo real.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CultoEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "culto-updates", groupId = "igreja-group")
    public void handleCultoUpdate(Object event) {
        log.info("Received culto update: {}", event);
        messagingTemplate.convertAndSend("/topic/cultos", event);
    }
}
