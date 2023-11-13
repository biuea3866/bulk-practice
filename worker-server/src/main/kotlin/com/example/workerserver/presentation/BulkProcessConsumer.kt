package com.example.workerserver.presentation

import com.example.workerserver.application.BulkProcessApplication
import com.example.workerserver.presentation.GroupId.BULK_PROCESS_GROUP
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class BulkController(
    private val bulkProcessApplication: BulkProcessApplication
) {
    @KafkaListener(
        topics = [Topic.BULK_REQUEST],
        groupId = BULK_PROCESS_GROUP
    )
    fun bulkRequest(
        @Payload request: ProcessBulk,
        acknowledgment: Acknowledgment
    ) {
        try {
            bulkProcessApplication.bulkProcess(
                requestLogId = request.bulkRequestId,
                applicantId = request.applicantId,
                sequenceIdx = request.sequenceIdx,
                end = request.end
            )
        } catch(e: Exception) {
            bulkProcessApplication.failProcess(
                requestLogId = request.bulkRequestId,
                applicantId = request.applicantId,
                sequenceIdx = request.sequenceIdx,
                failReason = e.message,
                end = request.end
            )
        } finally {
            acknowledgment.acknowledge()
        }
    }
}

data class ProcessBulk(
    val bulkRequestId: Long,
    val applicantId: Int,
    val sequenceIdx: Int,
    val end: Boolean
)

object Topic {
    const val BULK_REQUEST = "bulk-request"
}

object GroupId {
    const val BULK_PROCESS_GROUP = "bulk-request-group"
}