package com.example.workerserver.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.converter.JsonMessageConverter

@EnableKafka
@Configuration
class KafkaConsumerConfiguration {
    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var bootstrapServer: String

    @Bean
    fun <T> kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val config = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to this.bootstrapServer,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.qualifiedName,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.qualifiedName,
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
        )
        val consumerFactory = DefaultKafkaConsumerFactory<String, Any>(config)
        val factory = ConcurrentKafkaListenerContainerFactory<String, Any>()

        factory.setRecordMessageConverter(JsonMessageConverter())
        factory.consumerFactory = consumerFactory
        factory.setConcurrency(1)
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL

        return factory
    }
}