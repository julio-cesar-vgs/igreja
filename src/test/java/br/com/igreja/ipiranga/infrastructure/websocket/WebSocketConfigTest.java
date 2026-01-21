package br.com.igreja.ipiranga.infrastructure.websocket;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class WebSocketConfigTest {

    @Test
    void configureMessageBroker_ShouldEnableSimpleBrokerAndSetPrefix() {
        // Arrange
        WebSocketConfig webSocketConfig = new WebSocketConfig();
        MessageBrokerRegistry registry = mock(MessageBrokerRegistry.class);

        // Act
        webSocketConfig.configureMessageBroker(registry);

        // Assert
        verify(registry).enableSimpleBroker("/topic");
        verify(registry).setApplicationDestinationPrefixes("/app");
    }

    @Test
    void registerStompEndpoints_ShouldRegisterEndpoint() {
        // Arrange
        WebSocketConfig webSocketConfig = new WebSocketConfig();
        StompEndpointRegistry registry = mock(StompEndpointRegistry.class);
        StompWebSocketEndpointRegistration registration = mock(StompWebSocketEndpointRegistration.class);

        when(registry.addEndpoint(anyString())).thenReturn(registration);
        when(registration.setAllowedOrigins(anyString())).thenReturn(registration);

        // Act
        webSocketConfig.registerStompEndpoints(registry);

        // Assert
        verify(registry).addEndpoint("/ws-church");
        verify(registration).setAllowedOrigins("*");
        verify(registration).withSockJS();
    }
}
