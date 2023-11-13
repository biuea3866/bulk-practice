package com.example.apiserver.infrastructure.kafka

import com.example.apiserver.infrastructure.entity.BulkRequestLog
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class KafkaPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    fun publish(
        requestLog: BulkRequestLog,
        applicantId: Int
    ) {
        kafkaTemplate.send(
            MessageBuilder.withPayload(Event(
                requestLogId = requestLog.id,
                applicantId = applicantId
            ))
                .setHeader(KafkaHeaders.TOPIC, Topic.REQUEST_BULK)
                .build()
        )
    }
}

object Topic {
    const val REQUEST_BULK = "request-bulk"
}

class Event(
    val requestLogId: Long,
    val applicantId: Int
) {

}