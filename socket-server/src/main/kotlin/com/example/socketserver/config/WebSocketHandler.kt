package com.example.socketserver.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketHandler: WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // 브로커가 메시지를 전파할 때 사용하는 주제, 클라이언트는 해당 주제를 구독하여 메시지 수신
        registry.enableSimpleBroker("/polling")
        // 클라이언트에서 서버로 메시지를 전송할 때 사용하는 prefix
        registry.setApplicationDestinationPrefixes("/sub")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws").withSockJS()
    }
}