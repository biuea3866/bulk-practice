package com.example.apiserver.infrastructure.kafka

import com.example.apiserver.infrastructure.entity.BulkRequestLog
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class KafkaPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun publish(
        requestLogId: Long,
        applicantId: Int,
        sequenceIdx: Int,
        end: Boolean
    ) {
        logger.info("publish requestLogId: $requestLogId, applicantId: $applicantId, sequenceIdx: $sequenceIdx, end: $end")

        kafkaTemplate.send(
            MessageBuilder.withPayload(Event(
                bulkRequestId = requestLogId,
                applicantId = applicantId,
                sequenceIdx = sequenceIdx,
                end = end
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
    val bulkRequestId: Long,
    val applicantId: Int,
    val sequenceIdx: Int,
    val end: Boolean
) {

}