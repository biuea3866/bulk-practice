package com.example.apiserver.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.Serializable

@Component
class RedisInternalSubscriber(
    val objectMapper: ObjectMapper,
    private val simpleSendingOperations: SimpMessageSendingOperations
): MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        val bulkMessage = fromByteArray<BulkPublishMessage>(message.body)
        println("message: ${bulkMessage}")
        println("topic: ${message.channel}")
        simpleSendingOperations.convertAndSend(
            "/sub/bulk/${bulkMessage.requestLogId}",
            bulkMessage
        )
    }
}

fun <T> fromByteArray(byteArray: ByteArray): T {
    val byteArrayInputStream = ByteArrayInputStream(byteArray)
    val objectInput: ObjectInput = ObjectInputStream(byteArrayInputStream)
    val result = objectInput.readObject() as T
    objectInput.close()
    byteArrayInputStream.close()
    return result
}

data class BulkPublishMessage(
    val success: Boolean,
    val requestLogId: Long,
    val processCount: Int
)