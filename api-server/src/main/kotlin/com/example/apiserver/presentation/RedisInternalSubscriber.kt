package com.example.apiserver.presentation

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.Serializable

@Component
class RedisInternalSubscriber(
    val objectMapper: ObjectMapper,
    private val messagingTemplate: SimpMessagingTemplate
): MessageListenerAdapter() {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        val bulkMessage: BulkPublishMessage = Gson().fromJson(message.body.toString(Charsets.UTF_8), BulkPublishMessage::class.java)
        println("message: ${bulkMessage}")
        println("topic: ${message.channel}")
        println("destination: ${"/sub/bulk/${bulkMessage.requestLogId}"}")
        messagingTemplate.convertAndSend(
            "/sub/bulk/${bulkMessage.requestLogId}",
            bulkMessage.processCount.toString()
        )
    }
}

data class BulkPublishMessage (
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("requestLogId") val requestLogId: Long,
    @JsonProperty("processCount") val processCount: Int
) {
}