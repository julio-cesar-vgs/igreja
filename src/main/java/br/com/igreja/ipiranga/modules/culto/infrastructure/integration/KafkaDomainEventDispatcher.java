package br.com.igreja.ipiranga.modules.culto.infrastructure.integration;

import br.com.igreja.ipiranga.modules.culto.domain.event.CultoAtualizado;
import br.com.igreja.ipiranga.modules.culto.domain.event.ItemCultoAdicionado;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Dispatcher de Infraestrutura que escuta Eventos de Domínio e os propaga para o barramento Kafka.
 * <p>
 * Garante o desacoplamento: O Domínio não conhece o Kafka.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class KafkaDomainEventDispatcher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @EventListener
    public void handleCultoAtualizado(CultoAtualizado event) {
        // Envia o payload do culto para o tópico
        kafkaTemplate.send("culto-updates", event.getCulto());
    }

    @EventListener
    public void handleItemAdicionado(ItemCultoAdicionado event) {
        // Formato simplificado de notificação string, mantendo compatibilidade anterior
        String payload = event.getTipoItem() + "_ADDED:" + event.getItemId();
        kafkaTemplate.send("culto-updates", payload);
    }
}
