package br.com.igreja.ipiranga.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Componente Listener para eventos de domínio do contexto de Culto via Apache Kafka.
 * <p>
 * Este listener atua como uma ponte entre o barramento de eventos assíncrono (Kafka)
 * e os clientes conectados via WebSocket. Quando uma alteração ocorre no culto (ex: hino adicionado),
 * um evento é recebido aqui e propagado em tempo real para os dashboards frontend.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CultoEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Método tratador de mensagens do tópico "culto-updates".
     * <p>
     * Recebe o evento deserializado, loga a ocorrência e o retransmite para o tópico WebSocket "/topic/cultos".
     * </p>
     *
     * @param event O objeto de evento recebido da fila (convertido automaticamente via JSON message converter).
     */
    @KafkaListener(topics = "culto-updates", groupId = "igreja-group")
    public void handleCultoUpdate(Object event) {
        log.info("Received culto update: {}", event);
        messagingTemplate.convertAndSend("/topic/cultos", event);
    }
}
