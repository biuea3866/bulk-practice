package com.example.socketserver.endpoint

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class SocketEndPoint(
    private val simpleSendingOperations: SimpMessagingTemplate
) {
    // 클라이언트에서 웹소켓으로 메시지를 보낼 때 사용하는 엔드포인트
    @MessageMapping("/sub/bulk/{requestLogId}")
    @SendTo("/polling/{requestLogId}")
    fun polling(
        @DestinationVariable requestLogId: String,
        message: String
    ) {
        println("polling: ${message}")
        // 메시지 핸들러에서 처리한 결과를 전송할 소켓 엔드포인트
        simpleSendingOperations.convertAndSend("/polling/${requestLogId}", message)
    }
}

data class SubscribeMessage(
    val success: Boolean,
    val requestLogId: Long,
    val processCount: Int
)