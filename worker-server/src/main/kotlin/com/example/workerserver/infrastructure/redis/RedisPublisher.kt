package com.example.workerserver.infrastructure.redis

import com.example.workerserver.domain.BulkPublishMessage
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Component

@Component
class RedisPublisher(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val bulkChannelTopic: ChannelTopic
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun publish(message: BulkPublishMessage) {
        logger.info("publish topic: ${bulkChannelTopic.topic} message: $message")
        redisTemplate.convertAndSend(bulkChannelTopic.topic, message)
    }
}