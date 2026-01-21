package br.com.igreja.ipiranga.infrastructure.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuração da infraestrutura de WebSocket utilizando o protocolo STOMP (Streaming Text Oriented Messaging Protocol).
 * <p>
 * Define as regras de roteamento de mensagens entre o backend e o frontend (Publish-Subscribe).
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configura o message broker (agente de mensagens) da aplicação.
     *
     * @param config O registro do broker a ser configurado.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Habilita um broker em memória simples para destinos iniciados por "/topic"
        config.enableSimpleBroker("/topic");
        // Define o prefixo de destino para mensagens que são vinculadas a métodos anotados com @MessageMapping
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Registra os endpoints STOMP do Websocket.
     * <p>
     * O endpoint definido aqui ("/ws-church") é a URL que o cliente (JS) utiliza para se conectar inicialmento (Handshake).
     * </p>
     *
     * @param registry O registro de endpoints.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-church")
                .setAllowedOrigins("*") // Permite conexões de qualquer origem (CORS) - Ajustar em produção!
                .withSockJS(); // Habilita fallback para SockJS caso WebSocket não esteja disponível no navegador
    }
}
