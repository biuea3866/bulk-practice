package com.example.workerserver.config

import com.example.workerserver.domain.BulkPublishMessage
import com.example.workerserver.infrastructure.redis.RedisPublisher
import io.lettuce.core.ClientOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import io.lettuce.core.SocketOptions
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import java.time.Duration
import java.time.temporal.ChronoUnit

@Configuration
class RedisConfiguration(
    @Value("\${spring.redis.host}")
    private val redisHost: String,
    @Value("\${spring.redis.port}")
    private val redisPort: Int,
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val socketOption = SocketOptions.builder().connectTimeout(Duration.of(1, ChronoUnit.MINUTES)).build()
        val clientOptions = ClientOptions.builder().socketOptions(socketOption).autoReconnect(true).build()
        val clientConfig = LettuceClientConfiguration.builder().clientOptions(clientOptions).build()
        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.hostName = redisHost
        redisStandaloneConfiguration.port = redisPort
        return LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig)
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()

        redisTemplate.setConnectionFactory(redisConnectionFactory)
        redisTemplate.keySerializer = Jackson2JsonRedisSerializer(String::class.java)
        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(BulkPublishMessage::class.java)
        return redisTemplate
    }

    @Bean
    fun bulkChannelTopic(): ChannelTopic {
        return ChannelTopic("bulk")
    }


}