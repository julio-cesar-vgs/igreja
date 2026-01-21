package br.com.igreja.ipiranga.shared.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Classe base abstrata para representar Eventos de Domínio no sistema.
 * <p>
 * No contexto de Domain-Driven Design (DDD) e Arquitetura Orientada a Eventos (EDA),
 * um Evento de Domínio é algo significativo que aconteceu no domínio e que os especialistas
 * de domínio se preocupam. Eventos são imutáveis e nomeados no passado (ex: CultoRealizado, OfertaRecebida).
 * </p>
 * <p>
 * Esta classe fornece a estrutura básica comum a todos os eventos, como um identificador único
 * e o momento exato da ocorrência.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public abstract class DomainEvent {
    /**
     * Identificador único deste evento específico. Útil para idempotência e rastreamento.
     */
    private final UUID eventId;

    /**
     * Carimbo de data/hora de quando o evento ocorreu.
     */
    private final LocalDateTime occurredOn;

    /**
     * Construtor protegido que inicializa o evento com um ID único e a data/hora atual.
     * Deve ser chamado pelos construtores das subclasses concretas.
     */
    protected DomainEvent() {
        this.eventId = UUID.randomUUID();
        this.occurredOn = LocalDateTime.now();
    }

    /**
     * Obtém o identificador único do evento.
     *
     * @return UUID representando o ID do evento.
     */
    public UUID getEventId() {
        return eventId;
    }

    /**
     * Obtém a data e hora em que o evento ocorreu.
     *
     * @return LocalDateTime do momento da ocorrência.
     */
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }
}
