package com.example.apiserver.presentation

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller

@Controller
class SocketEndPoint(
    private val simpleSendingOperations: SimpMessageSendingOperations
) {
    // 클라이언트에서 웹소켓으로 메시지를 보낼 때 사용하는 엔드포인트
    @MessageMapping("/sub/bulk/{requestLogId}")
    // 메시지 핸들러에서 처리한 결과를 받을 수 있는 엔드포인트
    @SendTo("/polling/{requestLogId}")
    fun polling(
        @DestinationVariable requestLogId: String,
        message: String
    ): String {
        println("polling: ${message}")
        return "Current Count: $message"
    }
}

data class SubscribeMessage(
    val success: Boolean,
    val requestLogId: Long,
    val processCount: Int
)