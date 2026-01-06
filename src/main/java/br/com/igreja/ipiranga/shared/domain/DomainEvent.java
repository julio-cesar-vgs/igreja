package br.com.igreja.ipiranga.shared.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Interface base para eventos de domínio (Event-Driven Architecture).
 * Eventos representam algo que aconteceu no passado no domínio.
 */
public abstract class DomainEvent {
    private final UUID eventId;
    private final LocalDateTime occurredOn;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID();
        this.occurredOn = LocalDateTime.now();
    }

    public UUID getEventId() {
        return eventId;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }
}
