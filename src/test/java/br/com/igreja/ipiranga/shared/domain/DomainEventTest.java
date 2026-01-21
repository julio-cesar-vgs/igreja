package br.com.igreja.ipiranga.shared.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class DomainEventTest {

    private static class TestDomainEvent extends DomainEvent {
    }

    @Test
    void constructor_ShouldGenerateUniqueEventId() {
        // Act
        TestDomainEvent event1 = new TestDomainEvent();
        TestDomainEvent event2 = new TestDomainEvent();

        // Assert
        assertThat(event1.getEventId()).isNotNull();
        assertThat(event2.getEventId()).isNotNull();
        assertThat(event1.getEventId()).isNotEqualTo(event2.getEventId());
    }

    @Test
    void constructor_ShouldSetOccurredOnToCurrentTime() {
        // Arrange
        LocalDateTime beforeCreation = LocalDateTime.now();

        // Act
        TestDomainEvent event = new TestDomainEvent();

        // Assert
        LocalDateTime afterCreation = LocalDateTime.now();
        assertThat(event.getOccurredOn()).isNotNull();
        assertThat(event.getOccurredOn())
                .isAfterOrEqualTo(beforeCreation)
                .isBeforeOrEqualTo(afterCreation);
    }

    @Test
    void getEventId_ShouldReturnValidUUID() {
        // Act
        TestDomainEvent event = new TestDomainEvent();

        // Assert
        assertThat(event.getEventId()).isInstanceOf(UUID.class);
        assertThat(event.getEventId().toString()).matches(
                "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"
        );
    }

    @Test
    void getOccurredOn_ShouldReturnLocalDateTime() {
        // Act
        TestDomainEvent event = new TestDomainEvent();

        // Assert
        assertThat(event.getOccurredOn()).isInstanceOf(LocalDateTime.class);
    }

    @Test
    void multipleInstances_ShouldHaveDifferentEventIds() {
        // Act
        TestDomainEvent event1 = new TestDomainEvent();
        TestDomainEvent event2 = new TestDomainEvent();
        TestDomainEvent event3 = new TestDomainEvent();

        // Assert
        assertThat(event1.getEventId())
                .isNotEqualTo(event2.getEventId())
                .isNotEqualTo(event3.getEventId());
        assertThat(event2.getEventId()).isNotEqualTo(event3.getEventId());
    }

    @Test
    void occurredOn_ShouldBeImmutable() {
        // Arrange
        TestDomainEvent event = new TestDomainEvent();
        LocalDateTime originalOccurredOn = event.getOccurredOn();

        // Act - wait a small amount
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Assert - the occurred time should not change
        assertThat(event.getOccurredOn()).isEqualTo(originalOccurredOn);
    }

    @Test
    void eventId_ShouldBeImmutable() {
        // Arrange
        TestDomainEvent event = new TestDomainEvent();
        UUID originalEventId = event.getEventId();

        // Act - get it again
        UUID retrievedEventId = event.getEventId();

        // Assert - should be the same instance
        assertThat(retrievedEventId).isEqualTo(originalEventId);
    }
}
