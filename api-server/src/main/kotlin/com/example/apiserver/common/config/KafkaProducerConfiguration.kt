package com.example.apiserver.common.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@EnableKafka
@Configuration
class KafkaProducerConfiguration {
    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var bootstrapServer: String

    @Bean
    fun <T> producerFactory(): ProducerFactory<String, Any> {
        val config = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to this.bootstrapServer,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.qualifiedName,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to KafkaSerializer<T>()::class.java,
        )

        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun <T> kafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(this.producerFactory<T>())
    }
}