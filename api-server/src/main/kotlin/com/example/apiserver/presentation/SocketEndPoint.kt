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
    @MessageMapping("/sub/bulk/{requestLogId}")
    @SendTo("/polling")
    fun polling(
        @DestinationVariable requestLogId: Long
    ) {
        simpleSendingOperations.convertAndSend("/polling/$requestLogId", "hello")
    }
}