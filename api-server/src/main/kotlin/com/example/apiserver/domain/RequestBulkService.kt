package com.example.apiserver.domain

import com.example.apiserver.infrastructure.entity.BulkRequestLog
import com.example.apiserver.infrastructure.kafka.KafkaPublisher
import com.example.apiserver.infrastructure.mysql.BulkRequestLogRepository
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class RequestBulkService(
    private val bulkRequestLogRepository: BulkRequestLogRepository,
    private val kafkaPublisher: KafkaPublisher
) {
    fun execute(applicantIds: List<Int>): Long {
        val bulkRequestLog = this.bulkRequestLogRepository.save(BulkRequestLog(
            id = 0,
            startDate = ZonedDateTime.now(),
            totalCount = applicantIds.size,
            endDate = null
        ))

        applicantIds.forEachIndexed { index, applicantId ->
            this.kafkaPublisher.publish(
                requestLogId = bulkRequestLog.id,
                applicantId = applicantId,
                sequenceIdx = index,
                end = applicantIds.last() == applicantId,
            )
        }

        return bulkRequestLog.id
    }
}