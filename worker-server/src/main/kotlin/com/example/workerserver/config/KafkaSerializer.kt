package com.example.workerserver.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.apache.kafka.common.serialization.Serializer

class KafkaSerializer<T>: Serializer<T> {
    override fun serialize(topic: String?, data: T): ByteArray {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())

        return objectMapper.writeValueAsBytes(data)
    }
}