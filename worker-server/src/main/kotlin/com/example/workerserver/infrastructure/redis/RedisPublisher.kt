package com.example.workerserver.infrastructure.redis

import com.example.workerserver.domain.BulkPublishMessage
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Component

@Component
class RedisPublisher(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    fun publish(message: BulkPublishMessage) {
        redisTemplate.convertAndSend("bulk", message)
    }
}