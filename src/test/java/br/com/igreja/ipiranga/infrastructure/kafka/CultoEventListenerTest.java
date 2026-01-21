package br.com.igreja.ipiranga.infrastructure.kafka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CultoEventListenerTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private CultoEventListener cultoEventListener;

    @Test
    void handleCultoUpdate_ShouldSendMessageToWebSocketTopic() {
        // Arrange
        Object event = new Object();

        // Act
        cultoEventListener.handleCultoUpdate(event);

        // Assert
        verify(messagingTemplate, times(1)).convertAndSend("/topic/cultos", event);
    }

    @Test
    void handleCultoUpdate_WithNullEvent_ShouldStillSendMessage() {
        // Arrange
        Object event = null;

        // Act
        cultoEventListener.handleCultoUpdate(event);

        // Assert
        verify(messagingTemplate, times(1)).convertAndSend("/topic/cultos", event);
    }

    @Test
    void handleCultoUpdate_WithStringEvent_ShouldSendMessage() {
        // Arrange
        String event = "Test Event";

        // Act
        cultoEventListener.handleCultoUpdate(event);

        // Assert
        verify(messagingTemplate, times(1)).convertAndSend("/topic/cultos", event);
    }

    @Test
    void handleCultoUpdate_ShouldLogReceivedEvent() {
        // Arrange
        Object event = "Sample Event";

        // Act
        cultoEventListener.handleCultoUpdate(event);

        // Assert
        verify(messagingTemplate).convertAndSend(eq("/topic/cultos"), eq(event));
    }
}
